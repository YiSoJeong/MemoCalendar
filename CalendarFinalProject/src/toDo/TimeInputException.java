package toDo;

public class TimeInputException extends Exception {
	        // no-argument constructor specifies default error message
			public TimeInputException() {
				super("Start-time should be earlier than end-time.");
			}

			// constructor to allow customized error message
			public TimeInputException(String message) {
				super(message);
			}
}
