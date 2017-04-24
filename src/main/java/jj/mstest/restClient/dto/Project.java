package jj.mstest.restClient.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {
    String name;
    String status;
    String sourceLang;
    List<String> targetLangs;

    public Project() {
    }

    public Project(String name, String status, String sourceLang, List<String> targetLangs) {
        this.name = name;
        this.status = status;
        this.sourceLang = sourceLang;
        this.targetLangs = targetLangs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getSourceLang() {
        return sourceLang;
    }

    public void setSourceLang(String sourceLang) {
        this.sourceLang = sourceLang;
    }

    public List<String> getTargetLangs() {
        return targetLangs;
    }

    public void setTargetLangs(List<String> targetLangs) {
        this.targetLangs = targetLangs;
    }
}
