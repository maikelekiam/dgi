/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 */
package domainapp.modules.simple.dom.equipo;


import domainapp.modules.simple.dom.planta.Planta;
import domainapp.modules.simple.dom.planta.PlantaRepositorio;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.datanucleus.query.typesafe.TypesafeQuery;
import org.joda.time.LocalDate;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.EquipoMenu",
        repositoryFor = Equipo.class
)
@DomainServiceLayout(
        named = "Equipos",
        menuOrder = "10"
)
public class EquipoRepositorio {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar equipos: ")
    @MemberOrder(sequence = "1")
    public List<Equipo> listAll() {
        return repositoryService.allInstances(Equipo.class);
    }


    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Buscar equipo: ")
    @MemberOrder(sequence = "2")
    public List<Equipo> findByName(
            @ParameterLayout(named="Denominacion")
            final String denominacion
    ) {
        TypesafeQuery<Equipo> q = isisJdoSupport.newTypesafeQuery(Equipo.class);
        final QEquipo cand = QEquipo.candidate();
        q = q.filter(
                cand.denominacion.indexOf(q.stringParameter("denominacion")).ne(-1)
        );
        return q.setParameter("denominacion", denominacion)
                .executeList();
    }

    @Programmatic
    public Equipo findByNameExact(final String denominacion) {
        TypesafeQuery<Equipo> q = isisJdoSupport.newTypesafeQuery(Equipo.class);
        final QEquipo cand = QEquipo.candidate();
        q = q.filter(
                cand.denominacion.eq(q.stringParameter("denominacion"))
        );
        return q.setParameter("denominacion", denominacion)
                .executeUnique();
    }

    @Programmatic
    public void ping() {
        TypesafeQuery<Equipo> q = isisJdoSupport.newTypesafeQuery(Equipo.class);
        final QEquipo candidate = QEquipo.candidate();
        q.range(0,2);
        q.orderBy(candidate.denominacion.asc());
        q.executeList();
    }

    public static class CreateDomainEvent extends ActionDomainEvent<EquipoRepositorio> {}

    @Action(domainEvent = CreateDomainEvent.class)
    @ActionLayout(named = "Crear equipo")
    @MemberOrder(sequence = "3")
    public Equipo create(
            final @ParameterLayout(named="Denominacion") String denominacion,
            final @ParameterLayout(named="Modelo") String modelo,
            final @ParameterLayout(named="Horometro") double horometro,
            final @ParameterLayout(named="Horas") double horasProximoMantenimiento,
            final @ParameterLayout(named="Ultimo") LocalDate fechaUltimoMantenimiento,
            final @ParameterLayout(named="Porcentaje") double porcentajeDisponibilidadMensual,
            final @ParameterLayout(named="rpm") int rpm,
            final @ParameterLayout(named="Presion") double presionAceite,

            final @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named="Planta") Planta planta
    ) {
        return repositoryService.persist(new Equipo(denominacion,modelo,horometro,horasProximoMantenimiento,fechaUltimoMantenimiento,porcentajeDisponibilidadMensual,rpm,presionAceite, planta));
    }

    // ESTO ES PARA EL DROPDOWNLIST DEL CREAR EQUIPO
    public List<Planta> choices8Create() {return plantaRepositorio.listAll();}


    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    PlantaRepositorio plantaRepositorio;

}
