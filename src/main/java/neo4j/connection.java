package neo4j;

import org.neo4j.driver.v1.*;

import static org.neo4j.driver.v1.Values.parameters;

public class connection implements AutoCloseable
{
    private final Driver driver;

    public connection( String uri, String user, String password )
    {
        driver = GraphDatabase.driver( uri, AuthTokens.basic( user, password ) );
    }

    @Override
    public void close() throws Exception
    {
        driver.close();
    }

    public void printGreeting( final String message )
    {
        try ( Session session = driver.session() )
        {
            String greeting = session.writeTransaction( new TransactionWork<String>()
            {
                @Override
                public String execute( Transaction tx )
                {
                    StatementResult result = tx.run( "CREATE (a:Greeting) " +
                                    "SET a.message = $message " +
                                    "RETURN a.message + ', from node ' + id(a)",
                            parameters( "message", message ) );
                    return result.single().get( 0 ).asString();
                }
            } );
            System.out.println( greeting );
        }
    }

    public static void main( String... args ) throws Exception
    {
        try ( connection greeter = new connection( "bolt://localhost:7687", "neo4j", "Yu6A9t6a" ) )
        {
            greeter.printGreeting( "hello, world" );
        }
    }
}