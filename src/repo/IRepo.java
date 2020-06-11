package repo;

import model.ProgramState;

import java.util.List;

public interface IRepo
{
    void setProgramList(List<ProgramState> states);
    List<ProgramState> getProgramList();
    void logProgramStateExec(ProgramState state);
}
