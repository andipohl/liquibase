package liquibase.sqlgenerator.core;

import liquibase.database.Database;
import liquibase.database.core.MySQLDatabase;
import liquibase.exception.ValidationErrors;
import liquibase.util.StringUtils;
import liquibase.sql.Sql;
import liquibase.sql.UnparsedSql;
import liquibase.sqlgenerator.SqlGeneratorChain;
import liquibase.statement.core.AddDefaultValueStatement;

public class AddDefaultValueGeneratorMySQL extends AddDefaultValueGenerator {
    @Override
    public int getPriority() {
        return PRIORITY_DATABASE;
    }

    @Override
    public boolean supports(AddDefaultValueStatement statement, Database database) {
        return database instanceof MySQLDatabase;
    }

    @Override
    public ValidationErrors validate(AddDefaultValueStatement addDefaultValueStatement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        ValidationErrors validationErrors;

        validationErrors = super.validate(addDefaultValueStatement, database, sqlGeneratorChain);

        validationErrors.checkRequiredField("columnDataType", StringUtils.trimToNull(addDefaultValueStatement.getColumnDataType()));

        return validationErrors;
    }

    @Override
    public Sql[] generateSql(AddDefaultValueStatement statement, Database database, SqlGeneratorChain sqlGeneratorChain) {
        String sql = ((MySQLDatabase) database).getAddDefaultSQL(statement);

        return new Sql[]{
                new UnparsedSql(sql,
                       getAffectedColumn(statement))
        };
    }
}