### Cách lấy thông tin giá real time từ socket của Capital

Bước 1: Gọi API ```/api/v1/socketId/{page}``` để lấy thông tin của các channel socket mà Capital cung cấp

Ví dụ một kết quả trả về như sau:

```json
[
  {
    "socketId": "1657857463492", // Socket ID của mã
    "nameShort": "META", // Tên ngắn
    "nameFull": "META", // Tên đầy đủ
    "type": "share" // Loại thị trường (share = chứng khoán, forex = ngoại hối, crypto = tiền điện tử, ...
  },
  {
    "socketId": "15959415572223172",
    "nameShort": "NKLA",
    "nameFull": "NKLA",
    "type": "share"
  }
]
```

Bước 2: Connect đến socket

```
wss://prod-wcc.backend-capital.com/app/MvtsstCbmX?protocol=7&client=js&version=7.0.2&flash=false
```

Bước 3: Với mỗi một mã chứng khoán cần lấy thông tin, gửi message sau đến socket

```json
{
  "event": "pusher:subscribe",
  "data":
  {
    "auth": "",
    "channel": <socketId>
  }
}
```

với ```<socketId>``` là socketId của mã muốn lấy thông tin thu được ở bước 1

Ví dụ, cần lấy hai mã với socketId là ```1657857463492``` và ```15959415572223172```. Gửi lần lượt hai message như sau:

```json
{
  "event": "pusher:subscribe",
  "data":
  {
    "auth": "",
    "channel": "1657857463492"
  }
}
```

```json
{
  "event": "pusher:subscribe",
  "data":
  {
    "auth": "",
    "channel": "15959415572223172"
  }
}
```

Bước 4: Socket sẽ gửi liên tục message với thông tin như sau:

```json
{
  "channel": <socketId>,
  "event": "bbo",
  "data": "{\"ts\":<socketId>,\"bid\":<giá bán>,\"ask\":<giá mua>,\"bidV\":1000.0,\"askV\":1000.0}"
}
```

trong đó trường "data" sẽ chứa giá bán và mua. Các thông tin còn lại có thể bỏ qua

Bước 5: Socket này sẽ tự động ngắt kết nối sau 1 thời gian, cần phải check và lặp lại từ bước 2