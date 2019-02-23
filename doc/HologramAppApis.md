Example: 
1. Run the hologram app 
2. Get list of all available products : http://localhost:8080/hologram/v2/get/allProducts
3. Get list of all available templates : http://localhost:8080/hologram/v2/get/allTemplates
4. To generate the hologram gif run this request - 
curl -X POST \
  http://localhost:8080/hologram/v2/image/gif/generator \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 2b5eeda3-fa0e-47b8-8773-202ffa2c10c6' \
  -H 'cache-control: no-cache' \
  -d '{
	"productName" : "coca-cola",
	"offerText" : "Buy 2 get 15% off",
	"template" : "ROUND_ROTATION"
}'
5. To generate hologram video run this request
curl -X POST \
  http://localhost:8080/hologram/v2/image/video/generator \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 7eb9b8a4-c0d5-4411-b9ed-ff7a391bb294' \
  -H 'cache-control: no-cache' \
  -d '{
	"productName" : "coca-cola",
	"offerText" : "Buy 2 get 15% off",
	"template" : "ROUND_ROTATION"
}'4. Video and gif will get save in out/production/resources/img folder
