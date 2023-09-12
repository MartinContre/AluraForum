# Alura Forum API

The Alura Forum API is a Spring Boot REST API that manages a forum where you can
perform CRUD operations on posts, messages, users, and courses. 
The primary functionality of API is centered around performing CRUD operations that can be executed on each of the created entities, working as follows:

- Create a new record.
- List all records.
- Show a specific record.
- Update a record.
- Delete a record.

Additionally, it offers custom searches such as:

- Search for a topic by a course name.
- Search for a topic or response by an author's name.
- Search for a response by a topic's name.

## Functionality

Alura forum consists of four entities (User, Topic, Response or Course),
where you can perform CRUD actions and, in some cases, custom searches. 
To facilitate understanding, we use Swagger to document the API.

## Getting Started

1. Create a DB named `alura_forum`
2. Start the application `AluraForumApi.java`.
3. Visit http://localhost:8585/doc/swagger-ui/index.html#
4. Go to the login section to create a JWT token. You can use the following JSON format:
    ```json
    {
    "username": "Maria",
    "password": "148"
    }
    ```
5. Once the token is generated, copy it and look for the "Authorize" button in the
top-right corner of the page. Enter the generated token.
6. After authorization, you can test any of the methods.

## Usage

You can use this API to manage forum-related data. Here are some common actions 
you can perform:

* **Create a New Record**: Use the appropriate endpoint to create a new record.
* **List All Records**: Retrieve a list of all records using the corresponding endpoint.
* **Show a Specific Record**: Retrieve details of a specific record by using its ID.
* **Update a Record**: Update an existing record by making a PUT request to the
relevant endpoint.
* **Update a Specific Record**: Update an existing record by making a PUT request to the
  relevant endpoint adding the id number to the path.
* **Delete a Record**: Delete a record using the DELETE request on the respective endpoint.
* Additionally, you can take advantage of custom search endpoints for more specific queries.

## Cloning and Running the Project Locally

To use the project locally, follow these steps:
1. Clone the repository to your local machine.
2. Open the project in your preferred IDE.
3. Run AluraForumApi.java.
4. Perform tests, either in an API testing application or tool.

## Documentation

For comprehensive API documentation, please refer to the Swagger documentation available at http://localhost:8585/doc/swagger-ui/index.html#

## Contributing

Contributions are welcome! If you would like to contribute to the project, please follow the standard procedures for code review and pull requests.

## License
This project is licensed under the MIT License.

## Contact
If you have any questions or need assistance, feel free to reach out.