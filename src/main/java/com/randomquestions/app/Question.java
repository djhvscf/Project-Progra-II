package com.randomquestions.app;

public class Question extends Entity {

    private String statement;

    public Question(long id, String statement) {
        super(id);
        this.setStatement(statement);
    }

    public String getStatement() {
        return statement;
    }

    private void setStatement(String statement) {
        this.statement = statement;
    }
}
