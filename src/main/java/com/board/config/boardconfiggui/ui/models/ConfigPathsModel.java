package com.board.config.boardconfiggui.ui.models;

import java.util.Objects;

public class ConfigPathsModel {

    String xmlPath;
    String repoPath;
    String toolchainPath;
    String outputPath;


    public String getXmlPath() {
        return xmlPath;
    }

    public String getRepoPath() {
        return repoPath;
    }

    public void setXmlPath(String xmlPath) {
        this.xmlPath = xmlPath;
    }

    public void setRepoPath(String repoPath) {
        this.repoPath = repoPath;
    }

    public void setToolchainPath(String toolchainPath) {
        this.toolchainPath = toolchainPath;
    }

    public void setOutputPath(String outputPath) {
        this.outputPath = outputPath;
    }

    public String getToolchainPath() {
        return toolchainPath;
    }

    public String getOutputPath() {
        return outputPath;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ConfigPathsModel that = (ConfigPathsModel) o;
        return Objects.equals(xmlPath, that.xmlPath) && Objects.equals(repoPath, that.repoPath) && Objects.equals(toolchainPath, that.toolchainPath) && Objects.equals(outputPath, that.outputPath);
    }

    @Override
    public int hashCode() {
        return Objects.hash(xmlPath, repoPath, toolchainPath, outputPath);
    }

    @Override
    public String toString() {
        return "ConfigPathsModel{" +
                "xmlPath='" + xmlPath + '\'' +
                ", repoPath='" + repoPath + '\'' +
                ", toolchainPath='" + toolchainPath + '\'' +
                ", outputPath='" + outputPath + '\'' +
                '}';
    }
}
