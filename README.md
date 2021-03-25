# CouponService
Web service whose main function is to recommend a subset of a user's favorite products according to the amount of a gift coupon.
The sum of the prices of the subset should be as close as possible to the coupon amount without exceeding it.

## Installation:

 * ```mkdir workspace```

 * ```cd workspace```

 * ```git clone https://github.com/agusotto96/CouponService.git```

 * ```cd CouponService```

 * ```mvn spring-boot:run```

## Demo:
A demo is hosted at Heroku: https://infinite-cove-80203.herokuapp.com

Request example:
```
curl --location --request POST 'https://infinite-cove-80203.herokuapp.com/coupon' \
--header 'Content-Type: application/json' \
--data-raw '{
    "itemIds": [
        "MLA766066779",
        "MLA874724627",
        "MLA877668710",
        "MLA874788297",
        "MLA690392281"
    ],
    "amount": 500
}'
```
