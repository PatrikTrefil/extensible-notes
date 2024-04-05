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

## Usage

The application contains a window for writing notes in HTML. You can switch between source and preview mode (use menubar
or shortcut `Ctrl + m`). You can use the command palette to quickly move around the application (use
shortcut `Ctrl + p`).
To list all available commands type `Help` in the command palette.
Use `Ctrl + s` to save the note.
Use `Ctrl + o` to open a note.
Use `Ctrl + n` to create a new note.

## Plugin development

To create a new plugin create a new module, which *provides* implementation of the `Plugin` interface (see plugins
module).
If this module is added to the modulepath it will be automatically loaded on startup.

## Generate documentation

```bash
mvn site
```

Result will be in `target/site/`.
