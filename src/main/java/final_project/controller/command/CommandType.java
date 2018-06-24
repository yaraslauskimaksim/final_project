package final_project.controller.command;

import final_project.controller.command.administrator.ShowUserComment;
import final_project.controller.command.go_to_Command.GoToCommentCommand;
import final_project.controller.command.user.*;

public enum CommandType {
    LOGIN {
        {
            this.command = new LoginCommand();
        }
    },
    LOGOUT {
        {
            this.command = new LogoutCommand();
        }
    },
    REGISTER {
        {
            this.command = new RegisterCommand();
        }
    },
    LOCAL {
        {
            this.command = new LocaleCommand();
        }
    },
    COMMENT{
        {
            this.command = new CommentCommand();
        }
    },
    QUEST {
        {
            this.command = new QuestCommand();
        }
    },
    SINGLE_QUEST {
        {
            this.command = new SingleQuestCommand();
        }
    },
    GO_TO_COMMENT {
        {
            this.command = new GoToCommentCommand();
        }
    },
        SHOW {
        {
            this.command=new ShowUserComment();
        }
    };

    Command command;
    public Command getCurrentCommand() {
        return command;
    }
}
