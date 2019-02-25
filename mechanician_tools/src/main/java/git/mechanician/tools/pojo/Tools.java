package git.mechanician.tools.pojo;

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
@Table(name="tools")
public class Tools implements Serializable{

	@Id
	private String Id;//工具文本id


	private String task;//任务ID
	private String tools;//工具文本


	public String getId() {
		return Id;
	}

	public void setId(String Id) {
		this.Id = Id;
	}

	public String getTask() {
		return task;
	}

	public void setTask(String task) {
		this.task = task;
	}

	public String getTools() {		
		return tools;
	}
	public void setTools(String tools) {
		this.tools = tools;
	}


	
}
