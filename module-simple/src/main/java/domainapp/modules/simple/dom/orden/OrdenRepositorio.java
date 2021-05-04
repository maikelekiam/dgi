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
package domainapp.modules.simple.dom.orden;

import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.equipo.EquipoRepositorio;
import domainapp.modules.simple.dom.mantenimiento.Mantenimiento;
import domainapp.modules.simple.dom.mantenimiento.MantenimientoRepositorio;
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
        objectType = "simple.OrdenMenu",
        repositoryFor = Orden.class
)
@DomainServiceLayout(
        named = "Ordenes",
        menuOrder = "10"
)
public class OrdenRepositorio {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar ordenes: ")
    @MemberOrder(sequence = "1")
    public List<Orden> listAll() {
        return repositoryService.allInstances(Orden.class);
    }

    public static class CreateDomainEvent extends ActionDomainEvent<OrdenRepositorio> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "2")
    public Orden create(
            final @ParameterLayout(named="Nombre")String nombre,
            final @ParameterLayout(named="Fecha") LocalDate fecha,

            final @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named="Planta") Planta planta,

            final @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named="Equipo") Equipo equipo,

            final @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named="Mantenimiento") Mantenimiento mantenimiento

    ) {
        return repositoryService.persist(new Orden(nombre, fecha, planta, equipo, mantenimiento));
    }

    // ESTO ES PARA EL DROPDOWNLIST
    public List<Planta> choices2Create() {return plantaRepositorio.listAll();}

    // ESTO ES PARA EL DROPDOWNLIST
    public List<Equipo> choices3Create() {return equipoRepositorio.listAll();}

    // ESTO ES PARA EL DROPDOWNLIST
    public List<Mantenimiento> choices4Create() {return mantenimientoRepositorio.listAll();}

    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;

    @javax.inject.Inject
    PlantaRepositorio plantaRepositorio;

    @javax.inject.Inject
    EquipoRepositorio equipoRepositorio;

    @javax.inject.Inject
    MantenimientoRepositorio mantenimientoRepositorio;

}
