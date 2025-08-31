# English Helper Application

A full-stack application for learning English vocabulary with translations, built with Spring Boot (backend) and Vue.js (frontend).

## Project Structure

```
├── backend/                 # Spring Boot backend application
│   ├── src/main/java/      # Java source code
│   ├── src/main/resources/ # Configuration files
│   └── pom.xml            # Maven configuration
├── frontend/               # Vue.js frontend application
│   ├── src/               # Vue source code
│   ├── package.json       # Node.js dependencies
│   └── vue.config.js      # Vue configuration
├── docker-compose.yml      # Docker services configuration
└── README.md              # This file
```

## Prerequisites

- Java 11 or higher
- Maven 3.6 or higher
- Node.js 14 or higher
- Docker and Docker Compose
- MongoDB (will be started via Docker)

## Quick Start

### 1. Start the Infrastructure

```bash
# Start MongoDB and other services
docker-compose up -d mongo mongo-express
```

This will start:
- MongoDB on port 27017
- Mongo Express (web interface) on port 8081

### 2. Run the Backend

```bash
cd backend
mvn spring-boot:run
```

The backend will start on port 8088.

### 3. Run the Frontend

```bash
cd frontend
npm install
npm run serve
```

The frontend will start on port 8089.

### 4. Access the Application

- Frontend: http://localhost:8089
- Backend API: http://localhost:8088
- Mongo Express: http://localhost:8081

## API Endpoints

### Translation Endpoints (Public)
- `GET /api/translation/phrases` - Get all phrases
- `GET /api/translation/words` - Get all words
- `GET /api/translation/phrasal-verbs` - Get all phrasal verbs
- `GET /api/translation/all` - Get all translations
- `POST /api/translation/` - Create a new translation
- `PUT /api/translation/{id}` - Update a translation
- `DELETE /api/translation/{id}` - Delete a translation

### Authentication Endpoints
- `POST /api/auth/signin` - User login
- `POST /api/auth/signup` - User registration
- `POST /api/auth/refresh` - Refresh JWT token

## Testing

### Backend Tests

```bash
cd backend
mvn test
```

### Frontend Tests

```bash
cd frontend
npm run test:unit
```

### End-to-End Tests

```bash
cd frontend
npm run test:e2e
```

## Development

### Backend Development

The backend is a Spring Boot application with:
- Spring Security for authentication
- JWT for stateless authentication
- MongoDB for data storage
- RESTful API design

### Frontend Development

The frontend is a Vue.js application with:
- Vue Router for navigation
- Vuex for state management
- Bootstrap Vue for UI components
- Axios for HTTP requests

## Database Schema

### Translation Collection
```json
{
  "id": "string",
  "text": "string (English text)",
  "transcription": "string (IPA transcription)",
  "translation": "string (Russian translation)",
  "type": "enum (PHRASE, WORD, PHRASAL_VERB)"
}
```

### User Collection
```json
{
  "id": "string",
  "username": "string",
  "email": "string",
  "password": "string (encrypted)",
  "role": "Role object"
}
```

## Configuration

### Backend Configuration
- Port: 8088
- MongoDB connection: localhost:27017
- JWT secret: configurable via properties

### Frontend Configuration
- Port: 8089
- API base URL: http://localhost:8088

## Troubleshooting

### Common Issues

1. **MongoDB Connection Error**
   - Ensure Docker is running
   - Check if MongoDB container is started: `docker ps`
   - Verify MongoDB is accessible on port 27017

2. **Port Already in Use**
   - Change ports in `application.yml` (backend) or `vue.config.js` (frontend)
   - Kill processes using the ports: `lsof -ti:8088 | xargs kill -9`

3. **Frontend Build Issues**
   - Clear node_modules: `rm -rf node_modules && npm install`
   - Check Node.js version compatibility

### Logs

- Backend logs: Check console output when running `mvn spring-boot:run`
- Frontend logs: Check browser console and terminal output
- MongoDB logs: `docker logs <mongo-container-id>`

## Contributing

1. Fork the repository
2. Create a feature branch
3. Make your changes
4. Add tests for new functionality
5. Submit a pull request

## License

This project is licensed under the MIT License.
