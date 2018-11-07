package com.example.owner.myreposapplication.api;

import android.arch.core.executor.testing.InstantTaskExecutorRule;

import com.example.owner.myreposapplication.dto.RepoDto;
import com.example.owner.myreposapplication.utils.adapters.LiveDataCallAdapterFactory;

import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.JUnit4;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import okhttp3.mockwebserver.RecordedRequest;
import okio.BufferedSource;
import okio.Okio;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;


@RunWith(JUnit4.class)
public class RepoApiTest {
    @Rule
    public InstantTaskExecutorRule instantExecutorRule = new InstantTaskExecutorRule();

    private RepoApi service;

    private MockWebServer mockWebServer;

    @Before
    public void createService() throws IOException {
        mockWebServer = new MockWebServer();
        service = new Retrofit.Builder()
                .baseUrl(mockWebServer.url("/"))
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(new LiveDataCallAdapterFactory())
                .build()
                .create(RepoApi.class);
    }

    @After
    public void stopService() throws IOException {
        mockWebServer.shutdown();
    }

    /*
        Test 1 -> Test the rest api point using the mock server
     */
    @Test
    public void testAlbumsRestPointSuccessful() throws IOException, InterruptedException {
        enqueueResponse("data.json");
        List<RepoDto> repoDtoList = LiveDataTestUtil.getValue(service.getRepoLiveData("ihajat",1, 100)).body;

        RecordedRequest request = mockWebServer.takeRequest();
        assertThat(request.getPath(), is("/users/ihajat/repos?page=1&per_page=100"));
    }

    /*
        Test 2 -> Test the Repo Size is Correct
    */
    @Test
    public void testAlbumsSizeSuccessful() throws IOException, InterruptedException {
        enqueueResponse("data.json");
        List<RepoDto> repoDtoList = LiveDataTestUtil.getValue(service.getRepoLiveData("ihajat",1, 100)).body;

        assertThat(repoDtoList.size(), is(15));
    }

    /*
        Test 3 -> Test Gets First Repo Successfully
    */
    @Test
    public void testGetFirstAlbumSuccessful() throws IOException, InterruptedException {
        enqueueResponse("data.json");
        List<RepoDto> repoDtoList = LiveDataTestUtil.getValue(service.getRepoLiveData("ihajat",1, 100)).body;

        RepoDto repoDto = repoDtoList.get(0);
        assertThat(repoDto.getName(), is("albums"));
    }

    private void enqueueResponse(String fileName) throws IOException {
        enqueueResponse(fileName, Collections.emptyMap());
    }

    private void enqueueResponse(String fileName, Map<String, String> headers) throws IOException {
        InputStream inputStream = getClass().getClassLoader()
                .getResourceAsStream("api-response/" + fileName);
        BufferedSource source = Okio.buffer(Okio.source(inputStream));
        MockResponse mockResponse = new MockResponse();
        for (Map.Entry<String, String> header : headers.entrySet()) {
            mockResponse.addHeader(header.getKey(), header.getValue());
        }
        mockWebServer.enqueue(mockResponse
                .setBody(source.readString(StandardCharsets.UTF_8)));
    }
}