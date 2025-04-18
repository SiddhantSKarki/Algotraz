package entities;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

public class Room {

	//private Timer timer;

	private List<Question> questions;

	private String description;

	private int roomNumber;

    public Room(String roomPath, int roomNumber) {
        //this.timer = new Timer(0, 0, 0);
        this.questions = new ArrayList<>();
        this.description = loadDescription(roomPath);
		this.roomNumber = roomNumber;
        loadQuestions(roomPath);
	}

	private String loadDescription(String path) {
        File desc = new File(path, "problem_description.txt");
        try {
            return Files.readString(desc.toPath());
        } catch (IOException e) {
            System.err.println("Couldn't read description: " + e.getMessage());
            return "";
        }
    }

	private void loadQuestions(String path) {
        File rootDir = new File(path);
        File[] folders = rootDir.listFiles(File::isDirectory);
        if (folders == null) return;
		// [problem1, problem2, problem3, ...]
        for (File folder : folders) {

            Question q = null;
			switch (roomNumber) {
				case 1 -> q = new TrueFalseQuestion(folder.getPath());
				case 2 -> q = new MultipleChoiceQuestion(folder.getPath());
				case 3 -> q = new CodeQuestion(folder.getPath());
				default -> {
					System.err.println("Unknown room number: " + roomNumber);
					System.exit(1);
                }
			}

            if (q != null) questions.add(q);
        }
    }
    
	public String getDescription() {
		return description;
	}

	public List<Question> getQuestions() {
		return questions;
	}

	// public Timer getTimer() {
	// 	return null;
	// }
	// Create a database to read from and fill in attributes
}
