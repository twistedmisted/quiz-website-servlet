package com.epam.final_project.app;

public class Page {
    private String path;
    private boolean isRedirect;

    public Page(String path, boolean isRedirect) {
        this.path = path;
        this.isRedirect = isRedirect;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public boolean isRedirect() {
        return isRedirect;
    }

    public void setRedirect(boolean redirect) {
        isRedirect = redirect;
    }
}
