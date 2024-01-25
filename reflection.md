# Assignment G2 2023Q2 18

## YPA Development Reflection

### Reused KPA Components

Our team transformed the Kakuro Puzzle Assistant (KPA) into a Killer Sudoku Puzzle Assistant (YPA), carefully selecting components to reuse:

- **GUI Framework:** Remained largely unchanged, thanks to its adaptable MVC design. Minor adjustments were made for Killer Sudoku's interface.
- **Solver Algorithm:** Significantly modified to accommodate Killer Sudoku's unique cage constraints.
- **Undo/Redo:** Minimally altered to fit the new puzzle type.
- **File Parser:** Completely rewritten for Killer Sudoku's specific puzzle format.

### New Design Patterns

To enhance the YPA, we introduced two key design patterns:

- **Facade Pattern:** Simplified interactions between the GUI and the complex logic of puzzle solving, making the system easier to use and extend.
- **Strategy Pattern (within MVC):** Allowed dynamic selection of solving strategies, making our solver adaptable to various puzzle difficulties.

### Project Experience

Adapting the KPA to YPA highlighted the importance of understanding both the original code and the new puzzle requirements. Our team's approach emphasized:

- **Effective Collaboration:** Regular meetings and pair programming were crucial in navigating the project's challenges.
- **Satisfaction with Outcome:** We're proud of the final product, which stands as a testament to our ability to apply software engineering principles in a practical context.

### Generality and Flexibility

Our implementation is designed with future expansion in mind:

- The MVC and Facade patterns ensure a clean separation of concerns and simplify adding new features or adapting to other puzzles.
- Our architecture supports easy updates and integration of new functionalities with minimal changes required.

### Conclusion

The project was an invaluable learning experience in software development, from assessing code reusability to applying advanced design patterns. Our team is confident in the YPA's foundation and its potential for future enhancements.
