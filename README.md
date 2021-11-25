# easebackend-template

The standard boilerplate codebase for backend server of JAVA implementation

## Develop

### ENV

| software | version |
| -------- | ------- |
| jdk      | 8       |
| mysql    | 5.7     |

### Database Init

```sql
CREATE DATABASE template_dev
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE DATABASE template_test
  CHARACTER SET utf8mb4
  COLLATE utf8mb4_unicode_ci;

CREATE USER 'megaease'@'%'
  IDENTIFIED BY 'template';

GRANT ALL PRIVILEGES ON template_dev.* TO megaease@'%';
GRANT ALL PRIVILEGES ON template_test.* TO megaease@'%';

FLUSH PRIVILEGES;
```
