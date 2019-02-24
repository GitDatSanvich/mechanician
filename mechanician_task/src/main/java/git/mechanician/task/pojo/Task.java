package git.mechanician.task.pojo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
/**
 * 实体类
 * @author Administrator
 *
 */
@Entity
@Table(name="Task")
public class Task implements Serializable{

	@Id
	private String Id;//文章主键
	private String chapter1;//
	private String chapter2;//
	private String chapter3;//
	private String title;//标题
	private String main;//工作概要
	private String notice;//注意事项
	private String enable;//是否可用
	private String writer;//创建者

	public String getWriter() {
		return writer;
	}

	public void setWriter(String writer) {
		this.writer = writer;
	}

	public String getId() {
		return Id;
	}
	public void setId(String Id) {
		this.Id = Id;
	}

	public String getChapter1() {		
		return chapter1;
	}
	public void setChapter1(String chapter1) {
		this.chapter1 = chapter1;
	}

	public String getChapter2() {		
		return chapter2;
	}
	public void setChapter2(String chapter2) {
		this.chapter2 = chapter2;
	}

	public String getChapter3() {		
		return chapter3;
	}
	public void setChapter3(String chapter3) {
		this.chapter3 = chapter3;
	}

	public String getTitle() {		
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public String getMain() {		
		return main;
	}
	public void setMain(String main) {
		this.main = main;
	}

	public String getNotice() {		
		return notice;
	}
	public void setNotice(String notice) {
		this.notice = notice;
	}

	public String getEnable() {		
		return enable;
	}
	public void setEnable(String enable) {
		this.enable = enable;
	}
}
