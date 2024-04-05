# Extensible Notes

## Compile app

```bash
mvn compile
```

## Start app

To start app with the default plugins (all that are in the plugins module) use:

```bash
mvn install && mvn exec:exec -pl application
```

To add disable or add more plugins, edit the dependencies in the `application/pom.xml` file.

## Plugin development

To create a new plugin create a new module, which *provides* implementation of the `Plugin` interface (see plugins
module).
If this module is added to the modulepath it will be automatically loaded on startup.

## Generate documentation

```bash
mvn site
```

Result will be in `target/site/`.