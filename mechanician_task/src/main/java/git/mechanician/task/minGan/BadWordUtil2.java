package git.mechanician.task.minGan;

/**
 * @ClassName BadWordUtil2
 * @Author GitDatSanvich
 * @Date 2019/12/6 17:50
 **/

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.util.*;

/**
 * 参考DFA算法demo:http://blog.csdn.net/chenssy/article/details/26961957
 */
public class BadWordUtil2 {
    //敏感词库文件路径
    public static Set<String> words;
    public static String filePath = "F:\\Launcher\\keywords";
    public static Map<String, String> wordMap;
    //最大匹配规则
    public static int maxMatchType = 2;
    //最小匹配规则
    public static int minMatchTYpe = 1;

    static {
        BadWordUtil2.words = readTxtByLine(filePath);
        addBadWordToHashMap(BadWordUtil2.words);
    }

    public static Set<String> readTxtByLine(String path) {
        Set<String> keyWordSet = new HashSet<>();
        File file = new File(path);
        if (!file.exists()) {      //文件流是否存在
            return keyWordSet;
        }
        BufferedReader reader = null;
        String temp = null;
        //int line=1;
        try {
            //reader=new BufferedReader(new FileReader(file));这样在web运行的时候，读取会乱码
            reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), "UTF-8"));
            while ((temp = reader.readLine()) != null) {
                //System.out.println("line"+line+":"+temp);
                keyWordSet.add(temp);
                //line++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return keyWordSet;
    }

    /**
     * 检查文字中是否包含敏感字符，检查规则如下：<br>
     *
     * @param txt
     * @param beginIndex
     * @param matchType
     * @return 如果存在，则返回敏感词字符的长度，不存在返回0
     * @version 1.0
     */
    @SuppressWarnings({"rawtypes"})
    public static int checkBadWord(String txt, int beginIndex, int matchType) {
        boolean flag = false;    //敏感词结束标识位：用于敏感词只有1位的情况
        int matchFlag = 0;     //匹配标识数默认为0
        char word = 0;
        Map nowMap = wordMap;
        for (int i = beginIndex; i < txt.length(); i++) {
            word = txt.charAt(i);
            nowMap = (Map) nowMap.get(word);     //获取指定key
            if (nowMap != null) {     //存在，则判断是否为最后一个
                matchFlag++;     //找到相应key，匹配标识+1
                if ("1".equals(nowMap.get("isEnd"))) {       //如果为最后一个匹配规则,结束循环，返回匹配标识数
                    flag = true;       //结束标志位为true
                    if (minMatchTYpe == matchType) {    //最小规则，直接返回,最大规则还需继续查找
                        break;
                    }
                }
            } else {     //不存在，直接返回
                break;
            }
        }
        /*“粉饰”匹配词库：“粉饰太平”竟然说是敏感词
         * “个人”匹配词库：“个人崇拜”竟然说是敏感词
         * if(matchFlag < 2 && !flag){
            matchFlag = 0;
        }*/
        if (!flag) {
            matchFlag = 0;
        }
        return matchFlag;
    }

    /**
     * 判断文字是否包含敏感字符
     *
     * @param txt       文字
     * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return 若包含返回true，否则返回false
     * @version 1.0
     */
    public static boolean isContaintBadWord(String txt, int matchType) {
        boolean flag = false;
        for (int i = 0; i < txt.length(); i++) {
            int matchFlag = checkBadWord(txt, i, matchType); //判断是否包含敏感字符
            if (matchFlag > 0) {    //大于0存在，返回true
                flag = true;
            }
        }
        return flag;
    }

    /**
     * 替换敏感字字符
     *
     * @param txt
     * @param matchType
     * @param replaceChar 替换字符，默认*
     * @version 1.0
     */
    public static String replaceBadWord(String txt, int matchType, String replaceChar) {
        String resultTxt = txt;
        Set<String> set = getBadWord(txt, matchType);     //获取所有的敏感词
        Iterator<String> iterator = set.iterator();
        String word = null;
        String replaceString = null;
        while (iterator.hasNext()) {
            word = iterator.next();
            replaceString = getReplaceChars(replaceChar, word.length());
            resultTxt = resultTxt.replaceAll(word, replaceString);
        }

        return resultTxt;
    }

    /**
     * 获取文字中的敏感词
     *
     * @param txt       文字
     * @param matchType 匹配规则 1：最小匹配规则，2：最大匹配规则
     * @return
     * @version 1.0
     */
    public static Set<String> getBadWord(String txt, int matchType) {
        Set<String> sensitiveWordList = new HashSet<String>();

        for (int i = 0; i < txt.length(); i++) {
            int length = checkBadWord(txt, i, matchType);    //判断是否包含敏感字符
            if (length > 0) {    //存在,加入list中
                sensitiveWordList.add(txt.substring(i, i + length));
                i = i + length - 1;    //减1的原因，是因为for会自增
            }
        }

        return sensitiveWordList;
    }

    /**
     * 获取替换字符串
     *
     * @param replaceChar
     * @param length
     * @return
     * @version 1.0
     */
    private static String getReplaceChars(String replaceChar, int length) {
        StringBuilder resultReplace = new StringBuilder(replaceChar);
        for (int i = 1; i < length; i++) {
            resultReplace.append(replaceChar);
        }

        return resultReplace.toString();
    }

    /**
     * TODO 将我们的敏感词库构建成了一个类似与一颗一颗的树，这样我们判断一个词是否为敏感词时就大大减少了检索的匹配范围。
     *
     * @param keyWordSet 敏感词库
     * @author yqwang0907
     * @date 2018年2月28日下午5:28:08
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static void addBadWordToHashMap(Set<String> keyWordSet) {
        wordMap = new HashMap(keyWordSet.size());     //初始化敏感词容器，减少扩容操作
        String key = null;
        Map nowMap = null;
        Map<String, String> newWorMap = null;
        //迭代keyWordSet
        for (String s : keyWordSet) {
            key = s;    //关键字
            nowMap = wordMap;
            for (int i = 0; i < key.length(); i++) {
                char keyChar = key.charAt(i);       //转换成char型
                Object wordMap = nowMap.get(keyChar);       //获取

                if (wordMap != null) {        //如果存在该key，直接赋值
                    nowMap = (Map) wordMap;
                } else {     //不存在则，则构建一个map，同时将isEnd设置为0，因为他不是最后一个
                    newWorMap = new HashMap<String, String>();
                    newWorMap.put("isEnd", "0");     //不是最后一个
                    nowMap.put(keyChar, newWorMap);
                    nowMap = newWorMap;
                }

                if (i == key.length() - 1) {
                    nowMap.put("isEnd", "1");    //最后一个
                }
            }
        }
    }


//    public static void main(String[] args) {
//        Set<String> s = BadWordUtil2.words;
//        Map<String, String> map = BadWordUtil2.wordMap;
//
//        System.out.println("敏感词的数量：" + BadWordUtil2.wordMap.size());
//        String string = "你是我生命中最唯美的遇见，文字带我走进这个陌生的世界。\n" +
//                "\n" +
//                "　　 ---题记\n" +
//                "\n" +
//                "　　那年，七岁，走进了那间土坯房，遇见了你。\n" +
//                "\n" +
//                "　　那个清贫的年代，没有刻意的准备，就把鸡吧那双粘满泥巴的小脚丫洗干净，穿上妈妈自己做的布鞋子，还有在油灯下缝制的新书包，就欢天喜地的奔向村口的那间老屋---只有几排用木板搭建的课桌，村里唯一的一间教室，里面只有一个戴着眼睛的老夫子，据说是村里唯一的一名高中生。\n" +
//                "\n" +
//                "　　第一次，端坐在教室里，第一次，听老夫子教字。老夫子在黑板上写下一个大大“人”，我和小伙伴们都笑了，这不是比玩泥巴捏小人还简单吗？第一次知道了那是一个文字！\n" +
//                "\n" +
//                "　　我们都是人，简单的人。\n" +
//                "\n" +
//                "　　那声音，过了许多年，如今，仍然清晰地回荡在耳边：孩子们，这就是你们人生的第一节课，学习了一个“人”字，它就站在那里！无论以后，经历了多少风雨，多少曲折，你们都要永远地站着！\n" +
//                "\n" +
//                "　　文字，带我走进这个陌生的世界，舞台很大，我自狂欢！于是，缘起，我知道了生活的简单定义：认字，可以帮助你过上更好的生活。文字，吸引了我的视线，孩子那懵懵懂懂的记忆里，文字发芽了。\n" +
//                "\n" +
//                "　　那时，农村是清贫的，特别是多病的父亲和渐渐老去的奶奶，需要好多钱，而且家里也没有什么收入，只靠挣工分，哪里够用呀。尽管生活捉襟见肘，父亲还是坚持让我读书，希望走出大山，去谋求好的未来。太小的年龄，哪里能体会父母的心情？于是，放学后，还是疯狂地玩耍，甚至和别的孩子玩起了逃学的游戏，没少挨打。值得双亲欣慰的是，学习成绩一直居高不下。逐渐大了，看着疼痛的父亲，年迈的奶奶，还有瘦小的妈妈，突然觉得自己长大了。因为哥哥已经分家另过，大姐已经出嫁，只剩下一个比我大两岁的小姐，家里的活都落在了妈妈和小姐的肩上，心突然痛了。终于，从文字中，我知道了什么是生活的不容易，知道了艰难困苦，还有什么是沧桑的岁月！\n" +
//                "\n" +
//                "　　生活，是一本无字书，我却读出了很多滋味，在那个花季的日子里。\n" +
//                "\n" +
//                "　　那年，奶奶去世，看着不能送奶奶去坟地的父亲，我知道了死的含义，最后是哥哥代替父亲送奶奶去的坟地！那年，我十四岁。\n" +
//                "\n" +
//                "　　都说，一无所知的世界，一直走下去，才会有惊喜。可是，我已经一知半解了。\n" +
//                "\n" +
//                "　　那年，我十五岁，父亲饱经病魔的摧残，一米八的个子，躺在那里一动不动，母亲的手拉着她爱了一生的男人，舍不得他走。那一刻，我懂得了死的滋味。可能对于别人来说，没有什么，可是对于我和我的母亲来说，一座大山轰然倒塌！我懂得了死别的味道，一刹那，我成熟了：生老病死是人生的循环，无人阻挡得了。\n" +
//                "\n" +
//                "　　那年，我到县城读了高中。挥手告别站在村口那棵梨树下的母亲和姐姐，我读懂了她们眼睛里的希望！我知道了离别的滋味：是鹰，总要，离开巢穴，展翅翱翔！\n" +
//                "\n" +
//                "　　文字，让我读懂了离别的味道，还有思念的味道……\n" +
//                "\n" +
//                "　　花季的雨里，我如饥似渴地汲取着知识的甘露。清晨，那条洒满露珠的小路上，可以听到我朗朗的读书声；傍晚，那片落满夕阳色彩的田埂上，可以看到我执著的脚步 ；月朗星稀的夜晚，依着校园那昏黄的路灯下，依然有我孜孜不倦的背影……\n" +
//                "\n" +
//                "　　那个秋阳高照的日子，学校组织了一次关于花季的征文大赛。我跃跃欲试，几个昼夜的努力，一篇题为“文字飘在花季的天空”被张贴在校园的优秀征文栏里，好多同学纷纷议论：谁呀，写的真好！\n" +
//                "\n" +
//                "　　当周末回家，有点沾沾自喜地把这件事说给妈妈听的时候，只读了几年私塾学堂的母亲淡淡地笑了：孩子，花季虽美，文字更美。如果，你不努力，文字可以退出你花季的天空！\n" +
//                "\n" +
//                "　　我不懂，问道：文字会飘吗？它怎么能走出我的天空？\n" +
//                "\n" +
//                "　　母亲认真地说：文字，是有灵魂的。\n" +
//                "\n" +
//                "　　我似懂非懂，扭头看向姐姐：你懂吗？姐姐读完初中就没有再上学了，帮助母亲干活。姐姐微笑着，指了指房顶的小燕子：你看，燕子大了，总要飞出去的呀！\n" +
//                "\n" +
//                "　　仿若一扇心窗被打开：外面的世界很大，我却很小。恰似文字的海洋无限宽旷，而我只是沧海的一叶轻舟！\n" +
//                "\n" +
//                "　　文字，教我懂得了宽广的含义，还有爱的温暖！\n" +
//                "\n" +
//                "　　那年，十八岁，却遭遇了黑色的七月风暴，留在独木桥的此岸！\n" +
//                "\n" +
//                "　　默默地沿着那条熟悉的土路，一步一步地走着，很慢很慢，三十里的路一直走到了深夜！一个人，一颗落寞的心。那晚，恰恰，没有月亮！\n" +
//                "\n" +
//                "　　轻轻地推开，那间亮着灯光的门扉。只一眼，我的泪水哗哗流下：年迈的母亲斜依在椅子上，半睁着眼睛，似睡非睡，昏黄的灯光照在刻满皱纹的脸上，写满了沧桑，我却清晰地看见了母亲头上的根根白发，那样的刺眼，我的心很痛！不小了，我已经成年，实不该再让母亲扛起家的重担！\n" +
//                "\n" +
//                "　　以前，我只知道男人怎么写，此刻，我真正懂得了男人的责任和担当，还有亲情的无私！\n" +
//                "\n" +
//                "　　脱下衣服，走过去，轻轻地披在了母亲的身上。\n" +
//                "\n" +
//                "　　“回来了？怎么那么晚呀？吃饭了吗？一连串的担忧，挂在了母亲的心上。都说，男儿有泪不轻弹，此时，语言是多余的，我紧紧地抱住母亲那瘦弱的双肩，哽咽着：我，无缘独木桥的彼岸……\n" +
//                "\n" +
//                "　　子夜，是农村一天中最寂静的时候，而此时我的心，却似翻滚的海洋，奔驰的野马，何处是我，停泊的港湾，归巢之地？\n" +
//                "\n" +
//                "　　“孩子，今年不行，明年再考呀。并不是所有的努力都有回报的，比如妈妈，我把爱都给了你们，看见你们一个一个都长大，快乐地生活着，我就高兴。”\n" +
//                "\n" +
//                "　　子夜未央，心片片碎。文字，你让我用什么来描述此时的心情？无言，你教会了我是真！\n" +
//                "\n" +
//                "　　有人说，雨水，是天空倾泻而下的忧伤；孤独，是心底攀沿而上的渴望！我说，眼泪，是心底潺潺流淌的音符；寂静，是灵魂蠢蠢欲动的清泉！\n" +
//                "\n" +
//                "　　那年，送我到村口梨树下的只有我的母亲，小姐已经为人妻。风中的您，白发苍苍，弱弱的身躯，还有寂寞的眼神，都给了我向上的力量。那一刻，文字，让我诠释了母爱的伟大！\n" +
//                "\n" +
//                "　　那一刻，我成了故乡的离人，文字让我懂得了乡愁的含义！\n" +
//                "\n" +
//                "　　青春的记忆，镌刻在岁月的车轮上，用碎片串起人生的风铃，或浅浅，或张狂，任由文字述说。\n" +
//                "\n" +
//                "　　轻敲这些文字的时候，又是子夜十分。漂泊的无奈，思乡的难言，聆听静夜的声音，看谁的故事清瘦了心的方向？谁的执着赋予了岁月的沧桑？\n" +
//                "\n" +
//                "　　今晚，当清辉倾洒而下时，文字已经植入了我的似水年华。有些往事，让我雾气盈眸，泪湿心语；有些心事，云淡风轻，与岁月静酌！\n" +
//                "\n" +
//                "　　遇见文字，缘结一生，无法忘怀！";
//        System.out.println("待检测语句字数：" + string.length());
//        long beginTime = System.currentTimeMillis();
//        Set<String> set = BadWordUtil2.getBadWord(string, 2);
//        Boolean i = BadWordUtil2.isContaintBadWord(string, 2);
//        Boolean i2 = BadWordUtil2.isContaintBadWord("粉饰太平", 2);
//        Boolean i22 = BadWordUtil2.isContaintBadWord("粉饰太平", 1);
//        Boolean i3 = BadWordUtil2.isContaintBadWord("粉饰", 2);
//        Boolean i33 = BadWordUtil2.isContaintBadWord("粉饰", 1);
//        Boolean i4 = BadWordUtil2.isContaintBadWord("太平", 2);
//        Boolean i44 = BadWordUtil2.isContaintBadWord("太平", 1);
//        Boolean i5 = BadWordUtil2.isContaintBadWord("个人崇拜", 2);
//        Boolean i55 = BadWordUtil2.isContaintBadWord("个人崇拜", 1);
//        Boolean i6 = BadWordUtil2.isContaintBadWord("个人", 2);
//        Boolean i66 = BadWordUtil2.isContaintBadWord("个人", 1);
//        Boolean i7 = BadWordUtil2.isContaintBadWord("崇拜", 2);
//        Boolean i77 = BadWordUtil2.isContaintBadWord("崇拜", 1);
//        long endTime = System.currentTimeMillis();
//        System.out.println("语句中包含敏感词的个数为：" + set.size() + "。包含：" + set);
//        System.out.println("总共消耗时间为：" + (endTime - beginTime));
//    }


    public static void main(String[] args) {
        String string = "partner_openid=u00000001&timestamp=1546272000000";
        System.out.println(getSha1(string));
    }

    public static String getSha1(String str) {
        char[] hexDigits = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'a', 'b', 'c', 'd', 'e', 'f'};
        try {
            MessageDigest mdTemp = MessageDigest.getInstance("SHA1");
            mdTemp.update(str.getBytes(StandardCharsets.UTF_8));
            byte[] md = mdTemp.digest();
            int j = md.length;
            char[] buf = new char[j * 2];
            int k = 0;
            for (byte byte0 : md) {
                buf[k++] = hexDigits[byte0 >>> 4 & 0xf];
                buf[k++] = hexDigits[byte0 & 0xf];
            }
            return new String(buf);
        } catch (Exception e) {
            return null;
        }
    }
}
