package git.mechanician.tools.pojo;

import javax.persistence.Column;
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


	private String taskId;//任务ID
	private String tools;//工具文本

	
	public String getId() {
		return Id;
	}
	public void setId(String Id) {
		this.Id = Id;
	}

	public String getTaskId() {
		return taskId;
	}
	public void setTaskId(String taskId) {
		this.taskId = taskId;
	}

	public String getTools() {		
		return tools;
	}
	public void setTools(String tools) {
		this.tools = tools;
	}


	
}
