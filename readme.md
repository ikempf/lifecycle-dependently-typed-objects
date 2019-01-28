## Correctly model objects whose composition is tied to a domain defined lifecycle

Example
- User before creation: Basic info, No Id,       No hashed password
- User after creation:  Basic info, GeneratedId, Hashed password + salt
- etc.