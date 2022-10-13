package com.bikcodeh.melichallenge.util.extension

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class StringKtTest {

    private val data = """
            {
              "data": [{
                "type": "articles",
                "id": "1",
                "attributes": {
                  "title": "JSON:API paints my bikeshed!",
                  "body": "The shortest article. Ever.",
                  "created": "2015-05-22T14:56:29.000Z",
                  "updated": "2015-05-22T14:56:28.000Z"
                },
                "relationships": {
                  "author": {
                    "data": {"id": "42", "type": "people"}
                  }
                }
              }],
              "included": [
                {
                  "type": "people",
                  "id": "42",
                  "attributes": {
                    "name": "John",
                    "age": 80,
                    "gender": "male"
                  }
                }
              ]
            }
        """.trimIndent()

    private val encodeData = """
           ewogICJkYXRhIjogW3sKICAgICJ0eXBlIjogImFydGljbGVzIiwKICAgICJpZCI6ICIxIiwKICAgICJhdHRyaWJ1dGVzIjogewogICAgICAidGl0bGUiOiAiSlNPTjpBUEkgcGFpbnRzIG15IGJpa2VzaGVkISIsCiAgICAgICJib2R5IjogIlRoZSBzaG9ydGVzdCBhcnRpY2xlLiBFdmVyLiIsCiAgICAgICJjcmVhdGVkIjogIjIwMTUtMDUtMjJUMTQ6NTY6MjkuMDAwWiIsCiAgICAgICJ1cGRhdGVkIjogIjIwMTUtMDUtMjJUMTQ6NTY6MjguMDAwWiIKICAgIH0sCiAgICAicmVsYXRpb25zaGlwcyI6IHsKICAgICAgImF1dGhvciI6IHsKICAgICAgICAiZGF0YSI6IHsiaWQiOiAiNDIiLCAidHlwZSI6ICJwZW9wbGUifQogICAgICB9CiAgICB9CiAgfV0sCiAgImluY2x1ZGVkIjogWwogICAgewogICAgICAidHlwZSI6ICJwZW9wbGUiLAogICAgICAiaWQiOiAiNDIiLAogICAgICAiYXR0cmlidXRlcyI6IHsKICAgICAgICAibmFtZSI6ICJKb2huIiwKICAgICAgICAiYWdlIjogODAsCiAgICAgICAgImdlbmRlciI6ICJtYWxlIgogICAgICB9CiAgICB9CiAgXQp9
        """.trimIndent()

    @Test
    fun encode() {
        val result = data.encode()
        assertThat(result).isEqualTo(encodeData)
    }

    @Test
    fun decode() {
        val result = encodeData.decode()
        assertThat(result).isEqualTo(data)
    }
}
