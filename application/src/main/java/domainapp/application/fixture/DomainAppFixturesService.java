package domainapp.fixture;

import java.util.List;

import org.apache.isis.applib.annotation.ActionLayout;
import org.apache.isis.applib.annotation.DomainService;
import org.apache.isis.applib.annotation.DomainServiceLayout;
import org.apache.isis.applib.annotation.MemberOrder;
import org.apache.isis.applib.annotation.Where;
import org.apache.isis.applib.fixturescripts.FixtureResult;
import org.apache.isis.applib.fixturescripts.FixtureScripts;

import domainapp.application.fixture.scenarios.EquipoCreate;

@DomainService
@DomainServiceLayout(
        named="Ejemplo de Carga",
        menuBar = DomainServiceLayout.MenuBar.SECONDARY,
        menuOrder = "22"
)
public class DomainAppFixturesService extends FixtureScripts {

    @SuppressWarnings("deprecation")
	public DomainAppFixturesService() {
        super("domainapp.fixture");
    }
       
    @ActionLayout(hidden=Where.EVERYWHERE)
    public Object instalarFixturesEquipo() {
        final List<FixtureResult> Equipo = findFixtureScriptFor(EquipoCreate.class).run(null);
        return Equipo.get(0).getObject();
    }

	  @MemberOrder(sequence="99")
	  @ActionLayout(named="Cargar Datos de Prueba")
	    public String IntstalarTodosLosFixtures()
	    {
//	    	this.instalarFixturesClub();
	    	
//	    	this.instalarFixturesJugador();

	    	this.instalarFixturesEquipo();
	    	
//	    	this.instalarFixturesCuotasClub();
	    	
//	    	this.instalarFixturesTorneo();
//	    	
//	    	this.instalarFixturesDivision();
//	    	
//	    	this.instalarFixturesEquipo();
//	    		    	
	    	return "FIXTURES INSTALADOS";
	    }
 
}