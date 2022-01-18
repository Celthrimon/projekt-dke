# Endpoints myMood

`localhost:8080/mymood`

## Following-Service

`/following`
Bei jedem Request auf den Following-Service, muss der JWT im Header mitgegeben werden

### followUser

* GET
* Path: `/followUser/{userId}`
* Return: `List<User>` denen der angegebene Benutzer folgt

### followUser

* POST
* Path: `/followUser/{followerUserId}?user=followingUserId`
* Return: 201 Created

### followUser

* DELETE
* Path: `/followUser/{followerUserId}?user=followingUserId`
* Return: 200 Success

### followHashtag

* GET
* Path: `/followHashtag/{userId}`
* Return: `List<Hashtag>` denen der angegebene Benutzer folgt

### followHashtag

* POST
* Path: `/followHashtag/{userId}?hashtag=hashtagTitle`
* Return: 201

### followHashtag

* DELETE
* Path: `/followHashtag/{userId}?hashtag=hashtagTitle`
* Return: 200

### followerUser

* GET
* Path: `/followerUser/{userId}`
* Return: `JSONObject followerAndFollowing` Anzahl der gefolgten und der folgenden Benutzer

### followerHashtag

* GET
* Path: `/followerHashtag/{hashtagTitle}`
* Return: `int followerCount` Anzahl der Benutzer, die diesem Hashtag folgen

## Post-Service

`/post`

### posts

* GET
* Path: `/posts?[filterParameter]`
* Return: `List<Post>` abhängig von den filter Parametern.

### post

* POST
* Path: `/post` Body: `JSONObject<Post>` Header: JWT
* Return: 201

### post

* DELETE
* Path: `/post/{postId}` Header: JWT
* Return: 200

### like

* POST
* Path: `/like/{postId}?user=userId` Header: JWT
* Return: 201

### mood

* GET
* Path: `/mood/{userId}`
* Return: `JSONObject mood` liefert die aktuelle mood des Benutzers (letzte gepostete mood)

## User-Service

`/user`

### user

* POST
* Path: `/user` Body: `JSONObject<User>`
* Return: 201

### user

* PUT
* Path: `/user/{userId}` Body: `JSONObject<User>`
* Return: 200

### user

* DELETE
* Path: `/user/{userId}`
* Return: 200

### user

* GET
* Path: `/user/{userId}`
* Return: `JSONObject<User>`

### login

* POST
* Path: `/login` Body: `JSONObject<Credentials>`
* Return: JSON Web Token

### login

* GET
* Path: `/login` Body: `JWT`
* Return: isvalid : true/false

### refresh

* POST
* Path: `/refresh` Body: old JWT
* Return: new JWT

## Notification-Service

`/notification`

### notify

* POST
* Path: `/notify/{userId}?message=message`
* Return: 200

## Log-Service

`/log`

### log

* POST
* Path: `/log?message=message`
* Return: 201

### log

* GET
* Path: `/log?[filterParameter]`
* Return: `List<Log>`