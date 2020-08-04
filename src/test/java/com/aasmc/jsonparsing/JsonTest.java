package com.aasmc.jsonparsing;

import com.aasmc.jsonparsing.pojo.AuthorPOJO;
import com.aasmc.jsonparsing.pojo.BookPOJO;
import com.aasmc.jsonparsing.pojo.DayPOJO;
import com.aasmc.jsonparsing.pojo.SimpleTestCaseJsonPOJO;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class JsonTest {

    private String simpleTestCase = "{ \"title\" : \"aasmc\", \"author\": \"Alex\"}";
    private String dayScenario1 = "{\n" +
            "  \"date\": \"2019-12-25\",\n" +
            "  \"name\": \"Christmas Day\"\n" +
            "}";

    private String authorBookScenario = "{\n" +
            "\"authorName\": \"Alex\",\n" +
            "  \"books\": [\n" +
            "    {\n" +
            "      \"title\": \"title1\",\n" +
            "      \"inPrint\": true,\n" +
            "      \"publishDate\": \"2019-12-25\" \n" +
            "    },\n" +
            "    {\n" +
            "      \"title\": \"title2\",\n" +
            "      \"inPrint\": false,\n" +
            "      \"publishDate\": \"2019-01-01\" \n" +
            "    }\n" +
            "    \n" +
            "  ]\n" +
            "}";


    @org.junit.jupiter.api.Test
    void parse() throws IOException {
        JsonNode node = Json.parse(simpleTestCase);
        final String text = node.get("title").asText();
        assertEquals(text, "aasmc");
    }

    @Test
    void fromJson() throws IOException {
        JsonNode node = Json.parse(simpleTestCase);
        final SimpleTestCaseJsonPOJO pojo = Json.fromJson(node, SimpleTestCaseJsonPOJO.class);

        assertEquals(pojo.getTitle(), "aasmc");
    }

    @Test
    void toJson() {
        SimpleTestCaseJsonPOJO pojo = new SimpleTestCaseJsonPOJO();
        pojo.setTitle("Testing 123");
        final JsonNode jsonNode = Json.toJson(pojo);
        assertEquals(jsonNode.get("title").asText(), "Testing 123");
    }

    @Test
    void stringify() throws JsonProcessingException {
        SimpleTestCaseJsonPOJO pojo = new SimpleTestCaseJsonPOJO();
        pojo.setTitle("Testing 123");
        final JsonNode jsonNode = Json.toJson(pojo);
        final String stringify = Json.stringify(jsonNode);
        final String prettyPrint = Json.prettyPrint(jsonNode);
        System.out.println(stringify);
        System.out.println(prettyPrint);
    }

    @Test
    void dayTestScenario1() throws IOException {
        JsonNode node = Json.parse(dayScenario1);
        final DayPOJO pojo = Json.fromJson(node, DayPOJO.class);
        assertEquals("2019-12-25", pojo.getDate().toString());
    }

    @Test
    void authorBookScenario() throws IOException {
        JsonNode node = Json.parse(authorBookScenario);
        final AuthorPOJO pojo = Json.fromJson(node, AuthorPOJO.class);
        System.out.println("Author : " + pojo.getAuthorName());
        for (BookPOJO bp :
                pojo.getBooks()) {
            System.out.println("Book : " + bp.getTitle());
            System.out.println("Is in print : " + bp.isInPrint());
            System.out.println("Publish Date : " + bp.getPublishDate());
        }
    }

}