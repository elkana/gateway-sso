| Service           | Port  |
|-------------------|-------|
| Auth Service      | 8081  |
| User Service      | 8082  |
| Frontend          | 3000  |


# Port Conventions

| Port Range    | Description                                                               |
| ------------- | ------------------------------------------------------------------------- |
| `0–1023`      | *Well-known ports* (e.g. HTTP 80, HTTPS 443) – **avoid for custom apps**. |
| `1024–49151`  | *Registered ports* – many are used by well-known services. Still risky.   |
| `49152–65535` | *Dynamic/private/ephemeral ports* – **safest for dev use**.               |


| Strategy                       | Benefit                          |
| ------------------------------ | -------------------------------- |
| Dynamic ports (5000+)          | Avoids system/service collisions |
| Environment/config variables   | Easy to manage/change            |
| Reverse proxy/service registry | Centralized routing              |
| Docker or containers           | Isolated runtime environments    |
| Auto-detect free ports         | Seamless and hands-free          |
| Shared port registry           | Prevents team collisions         |


