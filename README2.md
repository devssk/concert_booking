## ERD
![erd.png](img/erd.png)

## 기술 스택
- Java 23
- Spring Boot 3.3.4
- RDB : MySQL
- ORM : JPA
- TEST : Junit + AssertJ
### Architecture
```
interfaces/
    account/
        AccountController.java
        AccountDto.java
    concert/
        ConcertController.java
        ConcertDto.java
    queue/
        QueueController.java
        QueueDto.java
    ResponseDto.java
    ResponseCode.java
application/
    facade
domain/
    entity
    service
    repository
infrastructure/
    repositoryImpl

```

## API 명세서

## 콘서트

## 1. **콘서트 정보 조회**
### **GET** `/concerts/{concertId}/info`

**설명:** 특정 콘서트의 정보를 조회합니다.

### **Request:**
- Path Parameters:
    - `concertId` (Long): 조회할 콘서트의 ID.

### **Response:**
- ResponseDto: 콘서트 정보 리스트.
    - `code` (String): 응답 코드 (예: "SUCC").
    - `data` (List of `ConcertInfoResponse`):
        - `concertId` (Long): 콘서트 ID.
        - `concertInfoId` (Long): 콘서트 정보 ID.
        - `concertName` (String): 콘서트 이름.
        - `concertDate` (String): 콘서트 날짜.

### **예시 응답:**
```json
{
  "code": "SUCC",
  "data": [
    {
      "concertId": 1,
      "concertInfoId": 1,
      "concertName": "2024 IU CONCERT HER",
      "concertDate": "2024-03-02"
    },
    {
      "concertId": 1,
      "concertInfoId": 2,
      "concertName": "2024 IU CONCERT HER",
      "concertDate": "2024-03-03"
    }
  ]
}
```

---

## 2. **콘서트 좌석 정보 조회**
### **GET** `/concerts/{concertId}/info/{concertInfoId}/seats`

**설명:** 특정 콘서트 정보에 대한 좌석 정보를 조회합니다.

### **Request:**
- Path Parameters:
    - `concertId` (Long): 콘서트 ID.
    - `concertInfoId` (Long): 콘서트 정보 ID.

### **Response:**
- ResponseDto: 콘서트 좌석 정보 리스트.
    - `code` (String): 응답 코드 (예: "SUCC").
    - `data` (List of `ConcertSeatResponse`):
        - `concertInfoId` (Long): 콘서트 정보 ID.
        - `concertSeatId` (Long): 좌석 ID.
        - `concertSeatNumber` (Integer): 좌석 번호.
        - `concertSeatStatus` (String): 좌석 상태 (예: "OPEN", "PAYMENT_WAIT", "CLOSE").

### **예시 응답:**
```json
{
  "code": "SUCC",
  "data": [
    {
      "concertInfoId": 1,
      "concertSeatId": 1,
      "concertSeatNumber": 1,
      "concertSeatStatus": "OPEN"
    },
    {
      "concertInfoId": 1,
      "concertSeatId": 2,
      "concertSeatNumber": 2,
      "concertSeatStatus": "OPEN"
    }
  ]
}
```

---

## 3. **좌석 예약**
### **POST** `/concerts/seats`

**설명:** 콘서트 좌석을 예약합니다. 결제가 되기 전까지 설정된 시간(ex. 5분)동안 좌석을 점유합니다.

### **Request:**
- Request Body:
    - `token` (String): 사용자 인증 토큰.
    - `concertInfoId` (Long): 예약할 콘서트 정보 ID.
    - `concertSeatId` (Long): 예약할 좌석 ID.

### **Request 예시:**
```json
{
  "token": "tempToken",
  "concertInfoId": 1,
  "concertSeatId": 5
}
```

### **Response:**
- ResponseDto: 예약된 좌석 정보.
    - `code` (String): 응답 코드 (예: "SUCC").
    - `data` (BookingSeatResponse):
        - `concertSeatId` (Long): 예약된 좌석 ID.
        - `expireMinute` (Integer): 임시 점유 시간 (분).

### **예시 응답:**
```json
{
  "code": "SUCC",
  "data": {
    "concertSeatId": 1,
    "expireMinute": 5
  }
}
```

---

## 4. **예약 정보 조회**
### **GET** `/concerts/booking/{memberId}/info/{bookingId}`

**설명:** 특정 회원의 예약 정보를 조회합니다.

### **Request:**
- Path Parameters:
    - `memberId` (Long): 회원 ID.
    - `bookingId` (Long): 예약 ID.

### **Response:**
- ResponseDto: 예약 정보.
    - `code` (String): 응답 코드 (예: "SUCC").
    - `data` (BookingInfoResponse):
        - `bookingId` (Long): 예약 ID.
        - `concertName` (String): 콘서트 이름.
        - `concertDate` (String): 콘서트 날짜.
        - `concertSeatNumber` (Integer): 예약된 좌석 번호.
        - `paymentAmount` (Long): 결제 금액.

### **예시 응답:**
```json
{
  "code": "SUCC",
  "data": {
    "bookingId": 1,
    "concertName": "2024 IU CONCERT HER",
    "concertDate": "2024-03-03",
    "concertSeatNumber": 1,
    "paymentAmount": 169000
  }
}
```

---

## 5. **회원 예약 리스트 조회**
### **GET** `/concerts/booking/{memberId}/info`

**설명:** 특정 회원의 모든 예약 정보를 조회합니다.

### **Request:**
- Path Parameters:
    - `memberId` (Long): 회원 ID.

### **Response:**
- ResponseDto: 예약 정보 리스트.
    - `code` (String): 응답 코드 (예: "SUCC").
    - `data` (List of `BookingInfoListResponse`):
        - `bookingId` (Long): 예약 ID.
        - `concertName` (String): 콘서트 이름.
        - `concertDate` (String): 콘서트 날짜.
        - `concertSeatNumber` (Integer): 예약된 좌석 번호.
        - `paymentAmount` (Long): 결제 금액.

### **예시 응답:**
```json
{
  "code": "SUCC",
  "data": [
    {
      "bookingId": 2,
      "concertName": "2024 IU CONCERT HER",
      "concertDate": "2024-03-02",
      "concertSeatNumber": 1,
      "paymentAmount": 169000
    },
    {
      "bookingId": 1,
      "concertName": "2024 IU CONCERT HER",
      "concertDate": "2024-03-03",
      "concertSeatNumber": 1,
      "paymentAmount": 169000
    }
  ]
}
```

## 계좌

## 1. **계좌 충전**
### **PATCH** `/accounts/charge`

**설명:** 회원의 계좌에 금액을 충전합니다.

### **Request:**
- Request Body:
    - `memberId` (Long): 회원 ID.
    - `amount` (Long): 충전할 금액.

### **Request 예시:**
```json
{
  "memberId": 1,
  "amount": 30000
}
```

### **Response:**
- ResponseDto: 충전 후 잔액 정보.
    - `code` (String): 응답 코드 (예: "SUCC").
    - `data` (ChargeResponse):
        - `balance` (Long): 충전 후 잔액.

### **예시 응답:**
```json
{
  "code": "SUCC",
  "data": {
    "balance": 30000
  }
}
```

---

## 2. **결제 처리**
### **PATCH** `/accounts/payment`

**설명:** 회원의 계좌에서 콘서트 예약에 대한 결제를 처리합니다. 좌석 완전 점유, 대기열 토큰 만료가 포함되어 있습니다. 결제 후 티케팅이 완료 됩니다.

### **Request:**
- Request Body:
    - `token` (String): 사용자 인증 토큰.
    - `concertInfoId` (Long): 예약할 콘서트 정보 ID.
    - `concertSeatId` (Long): 예약할 좌석 ID.
    - `amount` (Long): 결제할 금액.

### **Request 예시:**
```json
{
  "token": "user-auth-token",
  "concertInfoId": 1,
  "concertSeatId": 5,
  "amount": 169000
}
```

### **Response:**
- ResponseDto: 결제 정보.
    - `code` (String): 응답 코드 (예: "SUCC").
    - `data` (PaymentResponse):
        - `concertName` (String): 콘서트 이름.
        - `concertDate` (String): 콘서트 날짜.
        - `concertSeatNumber` (Integer): 좌석 번호.
        - `paymentAmount` (Long): 결제된 금액.

### **예시 응답:**
```json
{
  "code": "SUCC",
  "data": {
    "concertName": "2024 IU CONCERT HER",
    "concertDate": "2024-03-03",
    "concertSeatNumber": 1,
    "paymentAmount": 169000
  }
}
```

---

## 3. **계좌 잔액 조회**
### **GET** `/accounts/{memberId}/balance`

**설명:** 회원의 계좌 잔액을 조회합니다.

### **Request:**
- Path Parameters:
    - `memberId` (Long): 조회할 회원의 ID.

### **Response:**
- ResponseDto: 회원의 계좌 잔액 정보.
    - `code` (String): 응답 코드 (예: "SUCC").
    - `data` (AccountBalanceResponse):
        - `balance` (Long): 계좌 잔액.

### **예시 응답:**
```json
{
  "code": "SUCC",
  "data": {
    "balance": 30000
  }
}
```

## 대기열

## 1. **토큰 발급**
### **POST** `/queues/token`

**설명:** 사용자에게 대기열에 대한 토큰을 발급합니다. 대기열에 진입시키고 토큰을 반환합니다.

### **Request:**
- Request Body:
    - `userId` (Long): 사용자 ID.
    - `concertInfoId` (Long): 콘서트 정보 ID.

### **Request 예시:**
```json
{
  "userId": 1,
  "concertInfoId": 1
}
```

### **Response:**
- ResponseDto: 발급된 토큰 정보.
    - `code` (String): 응답 코드 (예: "SUCC").
    - `data` (IssueTokenResponse):
        - `token` (String): 발급된 토큰.

### **예시 응답:**
```json
{
  "code": "SUCC",
  "data": {
    "token": "tempToken"
  }
}
```

---

## 2. **대기 번호 조회**
### **GET** `/queues/waiting_number`

**설명:** 현재 사용자의 대기 번호를 조회합니다.

### **Request:**
- Path Parameters: 없음.

### **Response:**
- ResponseDto: 대기 번호 정보.
    - `code` (String): 응답 코드 (예: "SUCC").
    - `data` (WaitingNumberResponse):
        - `waitingNumber` (Integer): 현재 대기 번호.

### **예시 응답:**
```json
{
  "code": "SUCC",
  "data": {
    "waitingNumber": 5042
  }
}
```
