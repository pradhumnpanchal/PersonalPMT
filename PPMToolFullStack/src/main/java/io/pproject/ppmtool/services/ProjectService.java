package io.pproject.ppmtool.services;

import io.pproject.ppmtool.Repository.BacklogRepository;
import io.pproject.ppmtool.Repository.ProjectRepository;
import io.pproject.ppmtool.domain.Backlog;
import io.pproject.ppmtool.domain.Project;
import io.pproject.ppmtool.exceptions.ProjectIdException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProjectService {

    @Autowired
    private ProjectRepository projectRepository;

    @Autowired
    private BacklogRepository backlogRepository;

    public Project saveOrUpdateProject(Project project){
        try {
            project.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());

            if(project.getId() == null){
                Backlog backlog = new Backlog();
                project.setBacklog(backlog);
                backlog.setProject(project);
                backlog.setProjectIdentifier(project.getProjectIdentifier().toUpperCase());
            }

            if(project.getId() != null){
                project.setBacklog(backlogRepository.findByProjectIdentifier(project.getProjectIdentifier().toUpperCase()));
            }

            return projectRepository.save(project);

        }catch(Exception e){
            throw new ProjectIdException("Project ID '"+ project.getProjectIdentifier().toUpperCase() +"' already exists");
        }
    }

    public Project findProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier(projectId.toUpperCase());

        if(project == null)
            throw new ProjectIdException("Project ID '"+ projectId.toUpperCase() +"' does not exist");

        return project;
    }

    public Iterable<Project> findAllProjects(){
        return projectRepository.findAll();
    }

    public void DeleteProjectByIdentifier(String projectId){
        Project project = projectRepository.findByProjectIdentifier((projectId));

        if(project == null)
            throw new ProjectIdException("No Project with ID '"+ projectId+ "'");

        projectRepository.delete(project);
    }


}
