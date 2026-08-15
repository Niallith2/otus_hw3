## Домашнее задание
### Rest-assured 
#### Зауск тестов: mvn test -Dtest=RestAssuredTests -Dapi.baseUrl=https://fakerestapi.azurewebsites.net
Написать автотесты с использованием Rest-assured.
#### Мой результат выполнения:
```
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 3.401 s -- in RestAssuredTests
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 6, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  6.310 s
[INFO] Finished at: 2026-08-15T10:56:35+03:00
[INFO] ------------------------------------------------------------------------
```

### Wiremock
#### Зауск тестов: mvn test -Dtest=WiremockTests -Dapi.baseUrl=http://localhost:8089

```
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0, Time elapsed: 2.138 s -- in WiremockTests
[INFO] 
[INFO] Results:
[INFO] 
[INFO] Tests run: 2, Failures: 0, Errors: 0, Skipped: 0
[INFO] 
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time:  5.514 s
[INFO] Finished at: 2026-08-15T12:07:57+03:00
[INFO] ------------------------------------------------------------------------
```