package com.cs4650;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.services.youtube.YouTube;
import com.google.api.services.youtube.model.ResourceId;
import com.google.api.services.youtube.model.SearchListResponse;
import com.google.api.services.youtube.model.SearchResult;
import com.google.api.services.youtube.model.Thumbnail;
import com.google.common.collect.Lists;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

/**
 *
 * @author Ryan Tang
 */
public class App {

    private static YouTube youtube;
    private static final long NUMBER_OF_VIDEOS_RETURNED = 5;
    
    public static void main(String[] args) {

        List<String> scopes = Lists.newArrayList("https://www.googleapis.com/auth/youtube.force-ssl");
        String queryTerm = "car";
        
        try {
            // Authorize the request.
            Credential credential = Auth.authorize(scopes, "search");

            // This object is used to make YouTube Data API requests.
            youtube = new YouTube.Builder(Auth.HTTP_TRANSPORT, Auth.JSON_FACTORY, credential)
                    .setApplicationName("youtube-cmdline-captions-sample").build();

            YouTube.Search.List searchListRequest = youtube.search().list("id,snippet");
            searchListRequest.setQ(queryTerm);
            searchListRequest.setMaxResults(NUMBER_OF_VIDEOS_RETURNED);
            searchListRequest.setRegionCode("US");
            
            //System.out.println(searchListRequest.getVideoDuration());
            //searchListRequest.setVideoDuration("short");
            SearchListResponse results =  searchListRequest.execute();
            System.out.println(results);

            
            List<SearchResult> searchResultList = results.getItems();
            if (searchResultList != null) {
            	printSeacrhResults(searchResultList.iterator());
            }
            

        } catch (GoogleJsonResponseException e) {
            System.err.println("GoogleJsonResponseException code: " + e.getDetails().getCode()
                    + " : " + e.getDetails().getMessage());
            e.printStackTrace();
        } catch (IOException e) {
            System.err.println("IOException: " + e.getMessage());
            e.printStackTrace();
        } catch (Throwable t) {
            System.err.println("Throwable: " + t.getMessage());
            t.printStackTrace();
        }
    }
        
    private static void printSeacrhResults(Iterator<SearchResult> iteratorSearchResults) {

        if (!iteratorSearchResults.hasNext()) {
            System.out.println(" There aren't any results for your query.");
        }

        while (iteratorSearchResults.hasNext()) {

            SearchResult singleVideo = iteratorSearchResults.next();
            ResourceId rId = singleVideo.getId();
            
           // singleVideo.getFactory().
            
            if (rId.getKind().equals("youtube#video")) {
                Thumbnail thumbnail = singleVideo.getSnippet().getThumbnails().getDefault();
                System.out.println(" Video Id: " + rId.getVideoId());
                System.out.println(" Title: " + singleVideo.getSnippet().getTitle());
                System.out.println(" Thumbnail: " + thumbnail.getUrl());
                System.out.println(" Uploader: " + singleVideo.getSnippet().getChannelTitle());
                System.out.println(" Published On: " + singleVideo.getSnippet().getPublishedAt());
                System.out.println();
            }
        }
    }


}