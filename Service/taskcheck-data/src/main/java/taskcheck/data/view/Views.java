/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.data.view;

/**
 * The Interface BaseViews.
 */
public interface Views {

	/**
	 * The Class User.
	 */
	public class User {

	}

	/**
	 * The Interface PageView(Pagination).
	 */
	public interface PageView {

	}

	/**
	 * The Interface BaseEntity.
	 */
	public interface BaseEntity {

	}

	/**
	 * The Interface Role.
	 */
	public interface Role {

	}

	/**
	 * The Interface Release.
	 */
	public interface Release {

	}

	/**
	 * The Interface Project.
	 */
	public interface Project {

	}

	/**
	 * The Interface ProjectHoliday.
	 */
	public interface ProjectHoliday {

	}

	
	/**
	 * The Class ReleaseDetails.
	 */
	public class ReleaseDetails extends User implements Release, Project, BaseEntity {

	}
	
	/**
	 * The Class ReleaseDetails.
	 */
	public class ProjectHolidayDetails  implements ProjectHoliday, Project {

	}
	/*
	 * public class UserRole extends User implements Role {
	 * 
	 * }
	 */

	/**
	 * The Class ProjectDeatils.
	 */
	public class ProjectDeatils extends User implements BaseEntity, Issue, Epic, Sprint , Release {

	}

	/**
	 * The View for Projects' Users.
	 */
	public class ProjectUser extends User implements Role {

	}

	/**
	 * The Interface for Users' Project.
	 */
	public interface UserProject extends Project {

	}
	
	/**
	 * The Interface for Users' Project.
	 */
	public interface UserProjectDeatils extends Project , BaseEntity {

	}


	/**
	 * The Class for Projects.
	 */
	public class ProjectList extends ProjectUser implements Project {

	}

	/**
	 * The Class for Users' Details.
	 */
	public class UserDeatils extends User implements Role, UserProject {

	}

	

	/**
	 * The Interface Task.
	 */
	public interface Task extends BaseEntity {

	}

	/**
	 * The Interface Issue.
	 */
	public interface Issue extends BaseEntity {

	}

	/**
	 * The Interface Epic.
	 */
	public interface Epic extends BaseEntity {

	}

	/**
	 * The Interface Sprint.
	 */
	public interface Sprint extends BaseEntity {

	}

	/**
	 * The Class Story.
	 */
	public class Story extends User implements Epic,  BaseEntity {

	}
	
	/**
	 * The Class StoryDetails.
	 */
	public class StoryDetails extends User implements BaseEntity {

	}
	
	/**
	 * The Class StoryTaskDetails.
	 */
	public class StoryTaskDetails extends Story implements Task {

	}
	
	/**
	 * The Class Project Pagination.
	 */
	public class UserPageable extends User implements Role, UserProject, PageView {

	}

	/**
	 * The Class Project Pagination.
	 */
	public class ProjectPageable extends User implements BaseEntity, Project, PageView {

	}
	

	/**
	 * The Class Epic Pagination.
	 */
	public class EpicPageable extends User implements Epic, PageView {

	}

	/**
	 * The Class Issue Pagination.
	 */
	public class IssuePageable extends User implements Issue, Release, PageView {

	}

	/**
	 * The Class Sprint Pagination.
	 */
	public class SprintPageble extends User implements Sprint, PageView {

	}

	/**
	 * The Class StoryPageable.
	 */
	public class StoryPageable extends Story implements PageView {

	}

	/**
	 * The Class Task Pagination.
	 */
	public class TaskPageable extends User implements Task, PageView {

	}

	public class TaskWithUser extends User implements Task {

	}

	public class IssueWithUser extends User implements Issue {

	}

	public class Comment extends User implements BaseEntity {

	}
	
	public class Attachment extends User implements BaseEntity {

	}
	
	public class SprintLog implements BaseEntity {

	}
	
	public class SprintChart extends SprintLog implements Sprint {

	}
	
	

}
