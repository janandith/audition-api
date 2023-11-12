## **Commands**

**1. Build solution**

`./gradlew build`

**2. Run solution**

`./gradlew bootRun`

**Run solution in Debug Mode to enable Request/Response logs**

`./gradlew bootRun -debug`

## **APIs**
GET ALL POSTS

`http://localhost:8080/posts`

`ex: http://localhost:8080/posts`

GET ALL POSTS BETWEEN A RANGE

`http://localhost:8080/posts?start={startId}&end={endId}`

`ex: http://localhost:8080/posts?start=1&end=3`

GET ALL POSTS WITH POST ID

`http://localhost:8080/posts/{postId}`

`ex: localhost:8080/posts/1`

GET COMMENTS FOR POST ID

`http://localhost:8080/posts/{postId}/comments`

`ex: http://localhost:8080/posts/1/comments`

GET COMMENTS FOR PARTICULAR POST ID

`http://localhost:8080/comments/{postId}`

`ex: http://localhost:8080/comments/1`