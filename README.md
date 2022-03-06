# 1. Solution Design

## 1.1. Assumptions

- Cars cannot be booked more than once at the same time.
- Cars can be booked multiple times at different times.
- Cars can be rebooked after booking cancellation.
- After a booking is cancelled, it can be rebooked within the corresponding time.
- Assuming there are only two statuses, successful booking:`booked` and cancelled booking:`cancelled`.
- Not considering user registration and rental costs

## 1.2. Solution Implementation

1. Create two databases: one for cars and one for booking orders.

- `t_car`:

| field | type         | desc       |
|-------|--------------|------------|
| id    | bigint       | unique key |
| model | varchar(255) | car model  |

- `t_order`:

| field       | type     | desc                    |
|-------------|----------|-------------------------|
| id          | bigint   | unique key              |
| car_id      | bigint   | car id of booking order |
| status      | tinyint  | 0 cancelled; 1 booked   |
| start_time  | datetime | booking start time      |
| end_time    | datetime | booking end time        |
| create_time | datetime | order create time       |
| update_time | datetime | order update time       |

2. Get bookable cars during the given time range:

   ```sql
   select tc.*
   from t_order t
            right join t_car tc on t.car_id = tc.id
   where not exists(select t2.id
                    from t_order t2
                    where t2.car_id = tc.id
                      and t2.status = 1
                      and ((start_time <= #{startTime} and end_time >= #{startTime})
                        or (start_time <= #{endTime} and end_time >= #{endTime})))
   group by tc.id;
   ```

3. Use lock to avoid repeat booking of the same car under concurrency:
   ```java
   public class CarBookingService {
       public void bookingNew(){
         try {
            getLock(carId);
            // create new booking order
         } finally { 
            releaseLock(carId);
         }
       }
   }
    ```
# API Spec
1. Create a new booking order:
   1. **POST** `/api/booking/new`
   2. Request body:
      ```json
      {
        "carId": 1,
        "startTime": "2020-01-01 00:00:00",
        "endTime": "2020-02-01 01:00:00"
      }
      ```
   3. Response : **only http code == 200 is successful.**

         
2. Cancel a booking order.
   1. **GET** `/api/booking/cancel/{orderId}`
   2. `orderId` is the id of the booking order.
   3. Response : **only http code == 200 is successful.**

3. Get all bookable cars during the given time range.
   1. **GET** `/api/booking/bookableCars?startTime={startTime}&endTime={endTime}`
   2. `startTime` and `endTime` are required,format:`yyyy-MM-dd HH:mm:ss`.
   3. Response Body:
      ```json
      {
        "cars": [
          {
            "id": 1,
            "model": "BMW"
          },
          {
            "id": 2,
            "model": "Audi"
          }
        ]
      }
      ```