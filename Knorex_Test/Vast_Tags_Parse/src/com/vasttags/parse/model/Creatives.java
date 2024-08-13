package com.vasttags.parse.model;

import java.util.List;

public class Creatives {
	 private List<Companion> companions;
     private String duration;
     private List<TrackingEvents> trackingEvents;
     private List<VideoClicks> videoClicks;
     private List<MediaFiles> mediaFiles;
     
     
     
	public List<Companion> getCompanions() {
		return companions;
	}
	public void setCompanions(List<Companion> companions) {
		this.companions = companions;
	}
	public String getDuration() {
		return duration;
	}
	public void setDuration(String duration) {
		this.duration = duration;
	}
	public List<TrackingEvents> getTrackingEvents() {
		return trackingEvents;
	}
	public void setTrackingEvents(List<TrackingEvents> trackingEvents) {
		this.trackingEvents = trackingEvents;
	}
	public List<VideoClicks> getVideoClicks() {
		return videoClicks;
	}
	public void setVideoClicks(List<VideoClicks> videoClicks) {
		this.videoClicks = videoClicks;
	}
	public List<MediaFiles> getMediaFiles() {
		return mediaFiles;
	}
	public void setMediaFiles(List<MediaFiles> mediaFiles) {
		this.mediaFiles = mediaFiles;
	}
	public String getId() {
		// TODO Auto-generated method stub
		return null;
	}
     
     

}
