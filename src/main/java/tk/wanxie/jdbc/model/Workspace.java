package tk.wanxie.jdbc.model;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
@Table(name="workspace")
public class Workspace {

    @Id
    @GeneratedValue
    @Column(name="workspace_id")
    private int workspaceId;

    @Column(name="workspace_name")
    private String workspaceName;

                                                                    // mappedBy="workspaces" is the field property in the User class, this will use the user_workspace table for joining
    @ManyToMany(mappedBy="workspaces", fetch=FetchType.EAGER)       // which @ManyToMany in User class is doing the mapping, don't use fetch=FetchType.EAGER, fetch all users when Workspace is initialized
    private Collection<User> users = new ArrayList<>();

    public int getWorkspaceId() {
        return workspaceId;
    }

    public void setWorkspaceId(int workspaceId) {
        this.workspaceId = workspaceId;
    }

    public String getWorkspaceName() {
        return workspaceName;
    }

    public void setWorkspaceName(String workspaceName) {
        this.workspaceName = workspaceName;
    }

    public Collection<User> getUsers() {
        return users;
    }

    public void setUsers(Collection<User> users) {
        this.users = users;
    }

    @Override
    public String toString() {
        return "Workspace{" +
                "workspaceId=" + workspaceId +
                ", workspaceName='" + workspaceName + '\'' +
                ", users=" + users +
                '}';
    }
}
