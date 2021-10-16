package taskcheck.app.dto;

import java.util.List;

import taskcheck.data.repository.IssueRepository.ReleaseIssueCount;

public class IssueChartDto {
	
private long projectId;
	
	private List<ReleaseIssueCount> datapoints;

	public long getProjectId() {
		return projectId;
	}

	public void setProjectId(long projectId) {
		this.projectId = projectId;
	}

	public List<ReleaseIssueCount> getDatapoints() {
		return datapoints;
	}

	public void setDatapoints(List<ReleaseIssueCount> datapoints) {
		this.datapoints = datapoints;
	}

}
