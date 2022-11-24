TOKEN=$(curl -s -X POST "http://localhost:8080/api/v1/access/login" \
    -H  "accept: application/json" \
    -H  "Content-Type: application/json" \
    -d "{\"password\":    \"admin\",\"username\": \"admin@admin.com\"}" | jq -r '.token')

[ -z $TOKEN  ] && echo TOKEN empty && exit
curl -s \
    -H "Authorization: Bearer $TOKEN" http://localhost:8080/api/v1/access/info;echo
