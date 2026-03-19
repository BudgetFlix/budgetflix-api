# ⚙ BudgetFlix API

> Backend API for metadata and stream access in BudgetFlix.

---

##  Overview

The BudgetFlix API is responsible for:

*  Providing movie metadata
*  Handling access to video streams

It acts as the **entry point between the frontend and the streaming system**.

---

##  Responsibilities

* Serve movie data
* Authorize streaming requests
* Return stream entrypoints
* Keep streaming logic abstracted from the client

---

##  Endpoints

###  Get All Movies

```http id="ep1"
GET /api/movies
```

#### Response

```json id="ep1res"
[
  {
    "id": "1",
    "title": "Example Movie"
  }
]
```

---

### ️ Get Stream URL

```http id="ep2"
GET /api/stream/{id}
```

#### Response

```json id="ep2res"
{
  "url": "https://your-domain/stream/movie-id/index.m3u8"
}
```

---

##  Streaming Flow

```id="flow2"
Client → API → Nginx → HLS files
```

1. Client requests `/api/stream/{id}`
2. API validates request
3. API responds with stream URL or redirect
4. Nginx serves `.m3u8` and `.ts` files

> !! The API never serves video files directly. !!

---

##  Access Control

The API is responsible for:

* validating access to streams
* ensuring only authorized users can request playback

*(Authentication system is planned / in progress)*

---

## ️ Tech Stack

* Spring Boot
* Java
* REST API

---

##  Design Principles

* Keep API lightweight
* No video processing inside API
* Delegate streaming to Nginx
* Separate concerns (API vs media processing)


---

##  Future Improvements

* Authentication & user system
* JWT-based access control
* Rate limiting
* Movie details endpoint
* Search & filtering
* Pagination
* Admin endpoints

---

##  Philosophy

> The API should stay thin and focused.

It exists to:

* expose data
* control access
* connect systems

—not to handle heavy processing.

---

##  Related

* `budgetflix` → frontend
* `media-worker` → video processing & HLS generation

---

##  Status

>  Early stage (minimal endpoints, expanding soon)
