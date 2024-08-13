package com.vasttags.parse;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.vasttags.parse.model.Companion;
import com.vasttags.parse.model.Creatives;
import com.vasttags.parse.model.Impression;
import com.vasttags.parse.model.MediaFiles;
import com.vasttags.parse.model.TrackingEvents;
import com.vasttags.parse.model.VastTag;
import com.vasttags.parse.model.VideoClicks;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class VastXmlParser {
	
	public VastTag parseVastXml(String xmlContent) {
        VastTag vastModel = new VastTag();

        try {
            DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = factory.newDocumentBuilder();
            InputStream inputStream = new ByteArrayInputStream(xmlContent.getBytes(StandardCharsets.UTF_8));
            Document doc = builder.parse(inputStream);
            // Parse VAST version
            Node vastNode = doc.getElementsByTagName("VAST").item(0);
            Element vastElement = (Element) vastNode;
            vastModel.setVastTagVersion(vastElement.getAttribute("vastTagVersion"));

            // Parse Ad
            Node adNode = doc.getElementsByTagName("Ad").item(0);
            Element adElement = (Element) adNode;
            vastModel.setId(adElement.getAttribute("id"));

            // Parse InLine
            Node inlineNode = adElement.getElementsByTagName("InLine").item(0);
            Element inlineElement = (Element) inlineNode;

            vastModel.setTitle(getTextContent(inlineElement, "AdTitle"));
            vastModel.setDescription(getTextContent(inlineElement, "Description"));

            // Parse Impression
            Node impressionNode = inlineElement.getElementsByTagName("Impression").item(0);
            if (impressionNode != null) {
            	Impression impression = new Impression();
                Element impressionElement = (Element) impressionNode;
                impression.setId(impressionElement.getAttribute("id"));
                impression.setUrl(impressionElement.getTextContent());
                vastModel.setImpression(impression);
            }

            // Parse Creatives
            NodeList creativeNodes = inlineElement.getElementsByTagName("Creative");
            List<Creatives> creatives = new ArrayList<>();
            for (int i = 0; i < creativeNodes.getLength(); i++) {
                Node creativeNode = creativeNodes.item(i);
                Element creativeElement = (Element) creativeNode;
                Creatives creative = new Creatives();

                // Parse CompanionAds
                NodeList companionNodes = creativeElement.getElementsByTagName("Companion");
                List<Companion> companions = new ArrayList<>();
                for (int j = 0; j < companionNodes.getLength(); j++) {
                    Node companionNode = companionNodes.item(j);
                    Element companionElement = (Element) companionNode;
                    Companion companion = new Companion();
                    companion.setId(companionElement.getAttribute("id"));
                    companion.setWidth(Integer.parseInt(companionElement.getAttribute("width")));
                    companion.setHeight(Integer.parseInt(companionElement.getAttribute("height")));
                    companion.setType(companionElement.getElementsByTagName("StaticResource").item(0).getAttributes().getNamedItem("creativeType").getTextContent());
                    companion.setSource(getTextContent(companionElement, "StaticResource"));
                    companion.setClickThroughUrl(getTextContent(companionElement, "CompanionClickThrough"));
                    companions.add(companion);
                }
                creative.setCompanions(companions);

                // Parse Linear
                Node linearNode = creativeElement.getElementsByTagName("Linear").item(0);
                if (linearNode != null) {
                    Element linearElement = (Element) linearNode;
                    creative.setDuration(getTextContent(linearElement, "Duration"));

                    // Parse TrackingEvents
                    NodeList trackingEventNodes = linearElement.getElementsByTagName("Tracking");
                    List<TrackingEvents> trackingEvents = new ArrayList<>();
                    for (int k = 0; k < trackingEventNodes.getLength(); k++) {
                        Node trackingEventNode = trackingEventNodes.item(k);
                        Element trackingEventElement = (Element) trackingEventNode;
                        TrackingEvents trackingEvent = new TrackingEvents();
                        trackingEvent.setEventType(trackingEventElement.getAttribute("event"));
                        trackingEvent.setEventUrl(trackingEventElement.getTextContent());
                        trackingEvents.add(trackingEvent);
                    }
                    creative.setTrackingEvents(trackingEvents);

                    // Parse VideoClicks
                    NodeList videoClickNodes = linearElement.getElementsByTagName("ClickTracking");
                    List<VideoClicks> videoClicks = new ArrayList<>();
                    for (int l = 0; l < videoClickNodes.getLength(); l++) {
                        Node videoClickNode = videoClickNodes.item(l);
                        VideoClicks videoClick = new VideoClicks();
                        videoClick.setId(((Element) videoClickNode).getAttribute("id"));
                        videoClick.setUrl(videoClickNode.getTextContent());
                        videoClicks.add(videoClick);
                    }
                    creative.setVideoClicks(videoClicks);

                    // Parse MediaFiles
                    NodeList mediaFileNodes = linearElement.getElementsByTagName("MediaFile");
                    List<MediaFiles> mediaFiles = new ArrayList<>();
                    for (int m = 0; m < mediaFileNodes.getLength(); m++) {
                        Node mediaFileNode = mediaFileNodes.item(m);
                        Element mediaFileElement = (Element) mediaFileNode;
                        MediaFiles mediaFile = new MediaFiles();
                        mediaFile.setType(mediaFileElement.getAttribute("type"));
                        mediaFile.setBitrate(Integer.parseInt(mediaFileElement.getAttribute("bitrate")));
                        mediaFile.setWidth(Integer.parseInt(mediaFileElement.getAttribute("width")));
                        mediaFile.setHeight(Integer.parseInt(mediaFileElement.getAttribute("height")));
                        mediaFile.setSource(mediaFileElement.getTextContent());
                        mediaFiles.add(mediaFile);
                    }
                    creative.setMediaFiles(mediaFiles);
                }

                creatives.add(creative);
            }
            vastModel.setCreatives(creatives);

        } catch (Exception e) {
            e.printStackTrace();
        }

        return vastModel;
    }

    private String getTextContent(Element element, String tagName) {
        NodeList nodeList = element.getElementsByTagName(tagName);
        if (nodeList.getLength() > 0) {
            return nodeList.item(0).getTextContent().trim();
        }
        return null;
    }
	

	public static void main(String[] args) {
		
        String filePath = "C:/Users/Akash Patil/Desktop/Knorex_Test/Vast_Tags_Parse/src/com/vasttags/parse/VastTags.xml";;
        
		try {
            String xmlContent = new String(Files.readAllBytes(Paths.get(filePath)));

            VastXmlParser parser = new VastXmlParser();
            VastTag vastModel = parser.parseVastXml(xmlContent);

            System.out.println("VAST Version: " + vastModel.getVastTagVersion());
            System.out.println("Ad ID: " + vastModel.getId());
            System.out.println("Ad Title: " + vastModel.getTitle());
            System.out.println("Description: " + vastModel.getDescription());

            System.out.println("Impression ID: " + (vastModel.getImpression() != null ? vastModel.getImpression().getId() : "N/A"));
            System.out.println("Impression URL: " + (vastModel.getImpression() != null ? vastModel.getImpression().getUrl() : "N/A"));

            for (Creatives creative : vastModel.getCreatives()) {
                System.out.println("Creative Duration: " + creative.getDuration());

                for (Companion companion : creative.getCompanions()) {
                    System.out.println("Companion ID: " + companion.getId());
                    System.out.println("Companion Width: " + companion.getWidth());
                    System.out.println("Companion Height: " + companion.getHeight());
                    System.out.println("Companion Type: " + companion.getType());
                    System.out.println("Companion Source: " + companion.getSource());
                    System.out.println("Companion Click-Through URL: " + companion.getClickThroughUrl());
                }

                for (TrackingEvents trackingEvent : creative.getTrackingEvents()) {
                    System.out.println("Tracking Event Type: " + trackingEvent.getEventType());
                    System.out.println("Tracking Event URL: " + trackingEvent.getEventUrl());
                }

                for (VideoClicks videoClick : creative.getVideoClicks()) {
                    System.out.println("Video Click ID: " + videoClick.getId());
                    System.out.println("Video Click URL: " + videoClick.getUrl());
                }

                for (MediaFiles mediaFile : creative.getMediaFiles()) {
                    System.out.println("Media File Type: " + mediaFile.getType());
                    System.out.println("Media File Bitrate: " + mediaFile.getBitrate());
                    System.out.println("Media File Width: " + mediaFile.getWidth());
                    System.out.println("Media File Height: " + mediaFile.getHeight());
                    System.out.println("Media File Source: " + mediaFile.getSource());
                }
            }

        } catch (IOException e) {
            System.err.println("Error reading the XML file: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Error parsing the XML: " + e.getMessage());
        }
    }
		
	}


