# Students Record


## Tasks
- Familiarize yourself with `StudentRecord.java`. A `StudentRecord` contain the following fields:
    - Student ID
    - Assignments
    - Midterm
    - Final Exam
    - Final Mark
    - Letter Grade
- `StudentRecord` has four parameters (Student ID, Assignments, Midterm, Final Exam) and will calculate the final mark and letter grade for you.
- The GUI for the table is implemented for you. The rest of the GUI is up to you to implement.
- Add a form at the bottom for users to add a new record to the table, with four inputs:
    - Student ID
    - Midterm Grade
    - Assignments Grade
    - Final Exam
- Add a ‘File’ menu bar with the following menu items (`JMenuBar`, `JMenu` and `JMenuItem`):
    - New
    - Open
    - Save
    - Save As
    - Exit
- Create an instance variable called `String currentFilename`, which stores the currently viewed file’s directory
- When 'New' is selected, clear the table and set `currentFilename` to null (`clearTable()`).
- When 'Open' is selected, you will show a file chooser (`JFileChooser`) which will do the following when used:
    - Update `currentFilename`
    - Read the selected file, and update the table
- When 'Save As' is selected, you will show a file chooser which will do the following when used:
    - Update `currentFilename`
    - Save the table's content as a CSV file (check `data.csv` for a sample).
- When 'Save' is selected, which will do the following when used:
    - If `currentFilename` is null, show the file chooser to select the destination. Save the contents to the destination and update `currentFilename`.
    - If `currentFilename` is NOT null, save the contents to `currentFilename`.
- When 'Exit' is selected, exit the application