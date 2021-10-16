/*
 * Copyright (c) iHub 2021. All rights reserved. <br><br> 
 *
 */
package taskcheck.app.config;

import java.util.HashSet;
import java.util.Set;

import taskcheck.data.entity.Epic;
import taskcheck.data.entity.Issue;
import taskcheck.data.entity.Project;
import taskcheck.data.entity.Role;
import taskcheck.data.entity.Sprint;
import taskcheck.data.entity.Story;
import taskcheck.data.entity.Task;
import taskcheck.data.entity.User;

/**
 * The Interface ObjectMapper.
 */

public class ObjectMapper {

	/**
	 * Map user.
	 *
	 * @param dto the dto
	 * @param entity the entity
	 */
	public static void mapUser(User dto, User entity) {
		if (dto == null) {
			return;
		}

		if (dto.getId() != null) {
			entity.setId(dto.getId());
		}
		if (dto.getUsername() != null) {
			entity.setUsername(dto.getUsername());
		}
		if (dto.getFullName() != null) {
			entity.setFullName(dto.getFullName());
		}
		if (dto.getPassword() != null) {
			entity.setPassword(dto.getPassword());
		}
		if (entity.getRoles() != null) {
			Set<Role> set = dto.getRoles();
			if (set != null) {
				entity.getRoles().clear();

				for (Role o : set) {
					entity.getRoles().add(o);
				}

			}
		} else {
			Set<Role> set = dto.getRoles();
			if (set != null) {
				entity.setRoles(new HashSet<Role>(set));
			}
		}
		if (entity.getProjects() != null) {
			Set<Project> set1 = dto.getProjects();
			if (set1 != null) {
				entity.getProjects().clear();
				for (Project o : set1) {
					entity.getProjects().add(o);
				}
			}
		} else {
			Set<Project> set1 = dto.getProjects();
			if (set1 != null) {
				entity.setProjects(new HashSet<Project>(set1));
			}
		}
		if (dto.getCreatedBy() != null) {
			entity.setCreatedBy(dto.getCreatedBy());
		}
		if (dto.getCreatedAt() != null) {
			entity.setCreatedAt(dto.getCreatedAt());
		}
		if (dto.getUpdatedBy() != null) {
			entity.setUpdatedBy(dto.getUpdatedBy());
		}
		if (dto.getUpdatedAt() != null) {
			entity.setUpdatedAt(dto.getUpdatedAt());
		}
		entity.setEnabled(dto.isEnabled());
		entity.setVoided(dto.isVoided());
	}

	/**
	 * Map project.
	 *
	 * @param dto the dto
	 * @param entity the entity
	 */
	public static void mapProject(Project dto, Project entity) {
		if (dto == null) {
			return;
		}

		if (dto.getCreatedBy() != null) {
			if (entity.getCreatedBy() == null) {
				entity.setCreatedBy(new User());
			} else {
				entity.setCreatedBy(dto.getCreatedBy());
			}
		}
		if (dto.getCreatedAt() != null) {
			entity.setCreatedAt(dto.getCreatedAt());
		}
		if (dto.getUpdatedBy() != null) {
			if (entity.getUpdatedBy() == null) {
				entity.setUpdatedBy(new User());
			} else {
				entity.setUpdatedBy(dto.getUpdatedBy());
			}
		}
		if (dto.getUpdatedAt() != null) {
			entity.setUpdatedAt(dto.getUpdatedAt());
		}

		entity.setEnabled(dto.isEnabled());
		entity.setVoided(dto.isVoided());
		if (dto.getId() != null) {
			entity.setId(dto.getId());
		}
		if (dto.getCode() != null) {
			entity.setCode(dto.getCode());
		}
		if (dto.getName() != null) {
			entity.setName(dto.getName());
		}
		if (dto.getStatus() != null) {
			entity.setStatus(dto.getStatus());
		}
		if (dto.getLeaderName() != null) {
			entity.setLeaderName(dto.getLeaderName());
		}
		if (dto.getWorkWeek() != null) {
			entity.setWorkWeek(dto.getWorkWeek());
		}
		if (entity.getUsers() != null) {
			Set<User> set = dto.getUsers();
			if (set != null) {
				entity.getUsers().clear();
				for (User o : set) {
					entity.getUsers().add(o);
				}
			}
		} else {
			Set<User> set = dto.getUsers();
			if (set != null) {
				entity.setUsers(new HashSet<User>(set));
			}
		}
		if (entity.getSprints() != null) {
			Set<Sprint> set1 = dto.getSprints();
			if (set1 != null) {
				entity.getSprints().clear();
				for (Sprint o : set1) {
					entity.getSprints().add(o);
				}
			}
		} else {
			Set<Sprint> set1 = dto.getSprints();
			if (set1 != null) {
				entity.setSprints(new HashSet<Sprint>(set1));
			}
		}
		if (entity.getEpics() != null) {
			Set<Epic> set2 = dto.getEpics();
			if (set2 != null) {
				entity.getEpics().clear();
				for (Epic o : set2) {
					entity.getEpics().add(o);
				}
			}
		} else {
			Set<Epic> set2 = dto.getEpics();
			if (set2 != null) {
				entity.setEpics(new HashSet<Epic>(set2));
			}
		}
		if (entity.getIssues() != null) {
			Set<Issue> set3 = dto.getIssues();
			if (set3 != null) {
				entity.getIssues().clear();
				for (Issue o : set3) {
					entity.getIssues().add(o);
				}
			}
		} else {
			Set<Issue> set3 = dto.getIssues();
			if (set3 != null) {
				entity.setIssues(new HashSet<Issue>(set3));
			}
		}
	}

	/**
	 * Map epic.
	 *
	 * @param dto the dto
	 * @param entity the entity
	 */
	public static void mapEpic(Epic dto, Epic entity) {
		if (dto == null) {
			return;
		}

		if (dto.getId() != null) {
			entity.setId(dto.getId());
		}

		if (dto.getCreatedBy() != null) {
			if (entity.getCreatedBy() == null) {
				entity.setCreatedBy(new User());
			} else {
				entity.setCreatedBy(dto.getCreatedBy());
			}
		}
		if (dto.getCreatedAt() != null) {
			entity.setCreatedAt(dto.getCreatedAt());
		}
		if (dto.getUpdatedBy() != null) {
			if (entity.getUpdatedBy() == null) {
				entity.setUpdatedBy(new User());
			} else {
				entity.setUpdatedBy(dto.getUpdatedBy());
			}
		}
		if (dto.getUpdatedAt() != null) {
			entity.setUpdatedAt(dto.getUpdatedAt());
		}

		entity.setEnabled(dto.isEnabled());
		entity.setVoided(dto.isVoided());
		if (dto.getIdentifier() != null) {
			entity.setIdentifier(dto.getIdentifier());
		}
		if (dto.getName() != null) {
			entity.setName(dto.getName());
		}
		if (dto.getSummary() != null) {
			entity.setSummary(dto.getSummary());
		}
		if (dto.getDescription() != null) {
			entity.setDescription(dto.getDescription());
		}
		if (dto.getStatus() != null) {
			entity.setStatus(dto.getStatus());
		}
		if (dto.getPriority() != null) {
			entity.setPriority(dto.getPriority());
		}
		if (dto.getAssignee() != null) {
			if (entity.getAssignee() == null) {
				entity.setAssignee(new User());
			} else {
				entity.setAssignee(dto.getAssignee());
			}
		}
		if (dto.getProject() != null) {
			if (entity.getProject() == null) {
				entity.setProject(new Project());
			}
			mapProject(dto.getProject(), entity.getProject());
		}
		if (entity.getStories() != null) {
			Set<Story> set = dto.getStories();
			if (set != null) {
				entity.getStories().clear();

				for (Story o : set) {
					entity.getStories().add(o);
				}

			}
		} else {
			Set<Story> set = dto.getStories();
			if (set != null) {
				entity.setStories(new HashSet<Story>(set));
			}
		}
	}

	/**
	 * Map issue.
	 *
	 * @param dto the dto
	 * @param entity the entity
	 */
	public static void mapIssue(Issue dto, Issue entity) {
		if (dto == null) {
			return;
		}

		if (dto.getId() != null) {
			entity.setId(dto.getId());
		}

		if (dto.getCreatedBy() != null) {
			if (entity.getCreatedBy() == null) {
				entity.setCreatedBy(new User());
			} else {
				entity.setCreatedBy(dto.getCreatedBy());
			}
		}
		if (dto.getCreatedAt() != null) {
			entity.setCreatedAt(dto.getCreatedAt());
		}
		if (dto.getUpdatedBy() != null) {
			if (entity.getUpdatedBy() == null) {
				entity.setUpdatedBy(new User());
			} else {
				entity.setUpdatedBy(dto.getUpdatedBy());
			}
		}
		if (dto.getUpdatedAt() != null) {
			entity.setUpdatedAt(dto.getUpdatedAt());
		}

		entity.setEnabled(dto.isEnabled());
		entity.setVoided(dto.isVoided());
		if (dto.getIdentifier() != null) {
			entity.setIdentifier(dto.getIdentifier());
		}
		if (dto.getSummary() != null) {
			entity.setSummary(dto.getSummary());
		}
		if (dto.getDescription() != null) {
			entity.setDescription(dto.getDescription());
		}
		if (dto.getPriority() != null) {
			entity.setPriority(dto.getPriority());
		}
		if (dto.getSeverity() != null) {
			entity.setSeverity(dto.getSeverity());
		}
		if (dto.getStatus() != null) {
			entity.setStatus(dto.getStatus());
		}
		if (dto.getImpactedArea() != null) {
			entity.setImpactedArea(dto.getImpactedArea());
		}
		entity.setStoryPoint(dto.getStoryPoint());
		if (dto.getEnvironment() != null) {
			entity.setEnvironment(dto.getEnvironment());
		}
		if (dto.getResolvedBy() != null) {
			if (entity.getResolvedBy() == null) {
				entity.setResolvedBy(new User());
			} else {
				entity.setResolvedBy(dto.getResolvedBy());
			}
		}
		if (dto.getAssignee() != null) {
			if (entity.getAssignee() == null) {
				entity.setAssignee(new User());
			} else {
				entity.setAssignee(dto.getAssignee());
			}
		}
		if (dto.getVerifiedBy() != null) {
			if (entity.getVerifiedBy() == null) {
				entity.setVerifiedBy(new User());
			} else {
				entity.setVerifiedBy(dto.getVerifiedBy());
			}
		}
		if (dto.getProject() != null) {
			if (entity.getProject() == null) {
				entity.setProject(new Project());
			}
			mapProject(dto.getProject(), entity.getProject());
		}
		if (dto.getAffectVersion() != null) {
			entity.setAffectVersion(dto.getAffectVersion());
		}
		if (dto.getFixVersion() != null) {
			entity.setFixVersion(dto.getFixVersion());
		}
	}

	/**
	 * Map sprint.
	 *
	 * @param dto the dto
	 * @param entity the entity
	 */
	public static void mapSprint(Sprint dto, Sprint entity) {
		if (dto == null) {
			return;
		}

		if (dto.getId() != null) {
			entity.setId(dto.getId());
		}

		if (dto.getCreatedBy() != null) {
			if (entity.getCreatedBy() == null) {
				entity.setCreatedBy(new User());
			} else {
				entity.setCreatedBy(dto.getCreatedBy());
			}
		}
		if (dto.getCreatedAt() != null) {
			entity.setCreatedAt(dto.getCreatedAt());
		}
		if (dto.getUpdatedBy() != null) {
			if (entity.getUpdatedBy() == null) {
				entity.setUpdatedBy(new User());
			} else {
				entity.setUpdatedBy(dto.getUpdatedBy());
			}
		}
		if (dto.getUpdatedAt() != null) {
			entity.setUpdatedAt(dto.getUpdatedAt());
		}

		entity.setEnabled(dto.isEnabled());
		entity.setVoided(dto.isVoided());
		if (dto.getName() != null) {
			entity.setName(dto.getName());
		}
		if (dto.getStatus() != null) {
			entity.setStatus(dto.getStatus());
		}
		if (dto.getStartAt() != null) {
			entity.setStartAt(dto.getStartAt());
		}
		if (dto.getEndAt() != null) {
			entity.setEndAt(dto.getEndAt());
		}
		if (dto.getGoals() != null) {
			entity.setGoals(dto.getGoals());
		}
		if (dto.getProject() != null) {
			if (entity.getProject() == null) {
				entity.setProject(new Project());
			}
			mapProject(dto.getProject(), entity.getProject());
		}
		if (entity.getTasks() != null) {
			Set<Task> set = dto.getTasks();
			if (set != null) {
				entity.getTasks().clear();

				for (Task o : set) {
					entity.getTasks().add(o);
				}

			}
		} else {
			Set<Task> set = dto.getTasks();
			if (set != null) {
				entity.setTasks(new HashSet<Task>(set));
			}
		}
		if (entity.getIssue() != null) {
			Set<Issue> set1 = dto.getIssue();
			if (set1 != null) {
				entity.getIssue().clear();
				for (Issue o : set1) {
					entity.getIssue().add(o);
				}
			}
		} else {
			Set<Issue> set1 = dto.getIssue();
			if (set1 != null) {
				entity.setIssue(new HashSet<Issue>(set1));
			}
		}
	}

	/**
	 * Map story.
	 *
	 * @param dto the dto
	 * @param entity the entity
	 */
	public static void mapStory(Story dto, Story entity) {
		if (dto == null) {
			return;
		}

		if (dto.getCreatedBy() != null) {
			if (entity.getCreatedBy() == null) {
				entity.setCreatedBy(new User());
			} else {
				entity.setCreatedBy(dto.getCreatedBy());
			}
		}
		if (dto.getCreatedAt() != null) {
			entity.setCreatedAt(dto.getCreatedAt());
		}
		if (dto.getUpdatedBy() != null) {
			if (entity.getUpdatedBy() == null) {
				entity.setUpdatedBy(new User());
			} else {
				entity.setUpdatedBy(dto.getUpdatedBy());
			}
		}
		if (dto.getUpdatedAt() != null) {
			entity.setUpdatedAt(dto.getUpdatedAt());
		}

		entity.setEnabled(dto.isEnabled());
		entity.setVoided(dto.isVoided());
		if (dto.getId() != null) {
			entity.setId(dto.getId());
		}
		if (dto.getIdentifier() != null) {
			entity.setIdentifier(dto.getIdentifier());
		}
		if (dto.getSummary() != null) {
			entity.setSummary(dto.getSummary());
		}
		if (dto.getDescription() != null) {
			entity.setDescription(dto.getDescription());
		}
		if (dto.getType() != null) {
			entity.setType(dto.getType());
		}
		if (dto.getStatus() != null) {
			entity.setStatus(dto.getStatus());
		}
		if (dto.getPriority() != null) {
			entity.setPriority(dto.getPriority());
		}
		if (dto.getAssignee() != null) {
			if (entity.getAssignee() == null) {
				entity.setAssignee(new User());
			} else {
				entity.setAssignee(dto.getAssignee());
			}
		}
		if (dto.getEpic() != null) {
			if (entity.getEpic() == null) {
				entity.setEpic(new Epic());
			}
			mapEpic(dto.getEpic(), entity.getEpic());
		}
		if (entity.getTask() != null) {
			Set<Task> set = dto.getTask();
			if (set != null) {
				entity.getTask().clear();
				for (Task o : set) {
					entity.getTask().add(o);
				}
			}
		} else {
			Set<Task> set = dto.getTask();
			if (set != null) {
				entity.setTask(new HashSet<Task>(set));
			}
		}
	}

	/**
	 * Map task.
	 *
	 * @param dto the dto
	 * @param entity the entity
	 */
	public static void mapTask(Task dto, Task entity) {
		if (dto == null) {
			return;
		}

		if (dto.getId() != null) {
			entity.setId(dto.getId());
		}
		if (dto.getCreatedBy() != null) {
			if (entity.getCreatedBy() == null) {
				entity.setCreatedBy(new User());
			} else {
				entity.setCreatedBy(dto.getCreatedBy());
			}
		}
		if (dto.getCreatedAt() != null) {
			entity.setCreatedAt(dto.getCreatedAt());
		}
		if (dto.getUpdatedBy() != null) {
			if (entity.getUpdatedBy() == null) {
				entity.setUpdatedBy(new User());
			} else {
				entity.setUpdatedBy(dto.getUpdatedBy());
			}
		}
		if (dto.getUpdatedAt() != null) {
			entity.setUpdatedAt(dto.getUpdatedAt());
		}

		entity.setEnabled(dto.isEnabled());
		entity.setVoided(dto.isVoided());
		if (dto.getIdentifier() != null) {
			entity.setIdentifier(dto.getIdentifier());
		}
		if (dto.getSummary() != null) {
			entity.setSummary(dto.getSummary());
		}
		if (dto.getDescription() != null) {
			entity.setDescription(dto.getDescription());
		}
		if (dto.getType() != null) {
			entity.setType(dto.getType());
		}
		if (dto.getPriority() != null) {
			entity.setPriority(dto.getPriority());
		}
		if (dto.getSeverity() != null) {
			entity.setSeverity(dto.getSeverity());
		}
		if (dto.getStatus() != null) {
			entity.setStatus(dto.getStatus());
		}
		entity.setStoryPoint(dto.getStoryPoint());
		if (dto.getCompletedAt() != null) {
			entity.setCompletedAt(dto.getCompletedAt());
		}
		if (dto.getImpactedArea() != null) {
			entity.setImpactedArea(dto.getImpactedArea());
		}
		if (dto.getStory() != null) {
			if (entity.getStory() == null) {
				entity.setStory(new Story());
			} 
			mapStory(dto.getStory(), entity.getStory());
		}
		if (dto.getAssignee() != null) {
			if (entity.getAssignee() == null) {
				entity.setAssignee(new User());
			} else {
				entity.setAssignee(dto.getAssignee());
			}
		}
	}

}
