package com.vasttags.parse.model;

import java.util.List;

public class VastTag {
	private String vastTagVersion;
    private String Id;
    private String Title;
    private String Description;
    private Impression impression;
    private List<Creatives> creatives;
    
    
	public String getVastTagVersion() {
		return vastTagVersion;
	}
	public void setVastTagVersion(String vastTagVersion) {
		this.vastTagVersion = vastTagVersion;
	}
	public String getId() {
		return Id;
	}
	public void setId(String id) {
		Id = id;
	}
	public String getTitle() {
		return Title;
	}
	public void setTitle(String title) {
		Title = title;
	}
	public String getDescription() {
		return Description;
	}
	public void setDescription(String description) {
		Description = description;
	}
	public Impression getImpression() {
		return impression;
	}
	public void setImpression(Impression impression) {
		this.impression = impression;
	}
	public List<Creatives> getCreatives() {
		return creatives;
	}
	public void setCreatives(List<Creatives> creatives) {
		this.creatives = creatives;
	}
    
    
    

}
