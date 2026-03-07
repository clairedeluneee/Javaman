# Objective
Develop a console based Hangman game where the word changes every time the program runs.

# Constraints
1. Use OOP.
	- Create a class to run the program
	  and a class to handle the game logic.
	- Use private access modifiers.
	- Use a constructor.
	- Methods to organize the program.
    	- The game class must not be static.
2. Randomize the secret word.
	- Prepare at least 5 words.
	- A random word must be selected at startup.
	- The word must NOT be hardcoded.
		- No constraint on word count. Go wild.
3. Program must be feature-rich.
	- Display hidden letters with underscores.
	- User must guess one letter at a time.
	- Show remaining attempts.
	- Deduct attempt count on wrong guess.
	- Display messages on win / lose.
		- Add option to optionally restart on finish.
4. Account for edge cases.
	> We're dealing with user input.
	- Must handle the following:
		- Empty input
		- Non-letter input
		- Unexpected input errors
	- Program must NOT crash on invalid input.
5. Code must be organized.
	- Use meaningful variable names.
	- Use proper indentation.
	- Modulize whenever possible.
6. Document.
	- The program must include:
		- A program description
		- Documentation comments

# Submission requirements
- Must submit in a PDF format.
- Must compile and run.
- A demo may be required.
	- Inferred: We might need a GitHub repo.
	- Inferred: Have a demo ready.