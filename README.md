
# Project Title

This is the backend for Skill Enhancement Portal project.
A brief description of the available APIs.



## Authentication API Reference

#### Sign Up

```http
  POST /auth/signup
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. Your username|
| `email` | `string` | **Required**. Your email id |
| `password` | `string` | **Required**. Password for your account |
| `linkedin` | `string` | Link to your linkedin profile |
| `github` | `string` | Link to your github account |

#### Sign Up

```http
  POST /auth/signin
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `username` | `string` | **Required**. Your username (Unique) |
| `email` | `string` | **Required**. Your email id |
| `password` | `string` | **Required**. Password for your account |

#### Sign Out

```http
  POST /auth/signout
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `refreshToken` | `string` | **Required**. Refresh token provided while sign in |


## Users API Reference

#### Get User Profile

```http
  GET /users/{user_id}/profile
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `user_id` | `Long` | **Required**. User id to retrieve user's profile|

#### Follow User

```http
  POST /follow/{user_id}/
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `user_id` | `Long` | **Required**. User id to follow that user|

#### Fet User Followers List

```http
  GET /followers/{user_id}/
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `user_id` | `Long` | **Required**. User id to get that users's followers list|

#### Fet User Following List

```http
  GET /following/{user_id}/
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `user_id` | `Long` | **Required**. User id to get that users's following list|

#### Mark User As Spam

```http
  POST /spam/{user_id}/
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `user_id` | `Long` | **Required**. User id to mark user as spam|

#### Get User Spam Count

```http
  POST /spam/{user_id}/
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `user_id` | `Long` | **Required**. Get total number of spam reports of a user|


#### Get User Notifications

```http
  GET /notification/{user_id}/
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `user_id` | `Long` | **Required**. User Id to fetch user's notifications|

## Questions API Reference

#### Create Question

```http
  POST /ques/
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `postName` | `string` | **Required**. Subject of your question|
| `url` | `string` | Any associated links with the question/Refrence to some other question |
| `description` | `string` | **Required**. Detailed description of your question|
| `tag` | `Tag` |**Required**. Appropriate tag/category for the question |

##### Tag
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `name` | `string` | **Required**. Name of the tag|

#### Search Question

```http
  GET /ques/search
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `text` | `string` | **Required**. Your question query|
| `tag` | `Tag` | **Required**. Appropriate tag/category for the question |

#### Vote a Question

```http
  POST /ques/{question_id}/upvote
```
```http
  POST /ques/{question_id}/downvote
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `question_id` | `string` | **Required**. Id of the question to vote for|

#### List Trending Questions

```http
  GET /ques/
```
Lists the top 10 trending question based on the number of comments and number of upvotes.
#### Delete a Question (Only Owner)

```http
  DELETE /ques/{question_id}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `question_id` | `string` | **Required**. Id of the question to be deleted|

## Comments API Reference

#### Create a Comment

```http
  POST /comment/
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `q_id` | `string` | **Required**. Id of the question where we are posting comment|
| `url` | `string` | Any associated links with the comment/Refrence to some other question |
| `text` | `string` | **Required**. Description of your comment/answer|

#### Vote a Comment

```http
  POST /comment/{comment_id}/upvote
```
```http
  POST /comment/{comment_id}/downvote
```

| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `comment_id` | `string` | **Required**. Id of the comment to vote for|

#### Select Comment as Answer (Only for question owner)
```http
  POST /comment/{comment_id}/select
```
The owner of the question can select a particular comment as an acceptable solution to the question.

#### Delete a Comment (Only Owner)

```http
  DELETE /comment/{comment_id}
```
| Parameter | Type     | Description                |
| :-------- | :------- | :------------------------- |
| `comment_id` | `string` | **Required**. Id of the comment to be deleted|


## Features

- Users can follow each other
- Users get point for commenting, creating a question and getting there comment selected as answer.
- Users can mark another user as spam and if the spam count for a particular goes above 20 that user wont be able to access his/her account
- Full text search implemented to make question searching easier.


## MySQl Tables

![App Screenshot](https://drive.google.com/uc?export=view&id=1ifKmqQpK_g6U1y-vV7WgOcQGCcSuS0W7)

[comment]: <> (<img src="https://drive.google.com/uc?export=view&id=1ifKmqQpK_g6U1y-vV7WgOcQGCcSuS0W7" alt="alt text" width="600px" height="400px">)



## Tech Stack

**Server:** Spring boot  
**Data Base:** MySQL


  