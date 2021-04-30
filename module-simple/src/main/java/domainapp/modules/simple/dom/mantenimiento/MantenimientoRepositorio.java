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
package domainapp.modules.simple.dom.mantenimiento;

import domainapp.modules.simple.dom.compresor.Compresor;
import domainapp.modules.simple.dom.equipo.Equipo;
import domainapp.modules.simple.dom.equipo.EquipoRepositorio;
import domainapp.modules.simple.dom.motor.Motor;
import domainapp.modules.simple.dom.motor.MotorRepositorio;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;
import org.datanucleus.query.typesafe.TypesafeQuery;

import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.MantenimientoMenu",
        repositoryFor = Mantenimiento.class
)
@DomainServiceLayout(
        named = "Mantenimientos",
        menuOrder = "10"
)
public class MantenimientoRepositorio {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Mantenimientos")
    @MemberOrder(sequence = "1")
    public List<Mantenimiento> listAll() {
        return repositoryService.allInstances(Mantenimiento.class);
    }

    public static class CreateDomainEvent extends ActionDomainEvent<MantenimientoRepositorio> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @MemberOrder(sequence = "2")
    public Mantenimiento create(
            final @ParameterLayout(named="Nombre") String nombre,

            final @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named="Tipo") ETipoMantenimiento tipoMantenimiento,

            final @ParameterLayout(named="Equipo") Equipo equipo
            ) {
        return repositoryService.persist(new Mantenimiento(nombre, tipoMantenimiento, equipo));
    }

    // ESTO ES PARA EL DROPDOWNLIST
    public List<Equipo> choices2Create() {return equipoRepositorio.listAll();}

    @javax.inject.Inject
    EquipoRepositorio equipoRepositorio;


    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    IsisJdoSupport isisJdoSupport;
}
