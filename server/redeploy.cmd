@echo off
echo Stopping and removing old container...
docker-compose down

echo.
echo Building new image...
docker-compose build --no-cache

echo.
echo Starting container...
docker-compose up -d

echo.
echo Done! New version deployed.
pause