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
import domainapp.modules.simple.dom.compresor.CompresorRepositorio;
import domainapp.modules.simple.dom.planta.Planta;
import org.apache.isis.applib.annotation.*;
import org.apache.isis.applib.services.eventbus.ActionDomainEvent;
import org.apache.isis.applib.services.jdosupport.IsisJdoSupport;
import org.apache.isis.applib.services.repository.RepositoryService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@DomainService(
        nature = NatureOfService.VIEW_MENU_ONLY,
        objectType = "simple.MantenimientoCompresorMenu",
        repositoryFor = MantenimientoCompresor.class
)
@DomainServiceLayout(
        named = "Mantenimientos",
        menuOrder = "10"
)
public class MantenimientoCompresorRepositorio {

    @Action(semantics = SemanticsOf.SAFE)
    @ActionLayout(bookmarking = BookmarkPolicy.AS_ROOT, named = "Listar Mantenimientos")
    @MemberOrder(sequence = "1")
    public List<MantenimientoCompresor> listAll() {
        return repositoryService.allInstances(MantenimientoCompresor.class);
    }

    public static class CreateDomainEvent extends ActionDomainEvent<MantenimientoCompresorRepositorio> {}
    @Action(domainEvent = CreateDomainEvent.class)
    @ActionLayout(named = "Crear mantenimiento")
    @MemberOrder(sequence = "2")
    public MantenimientoCompresor create(
            final @Parameter(optionality = Optionality.MANDATORY)
            @ParameterLayout(named="Tipo") ETipoMantenimientoCompresor tipoMantenimientoCompresor,
            @ParameterLayout(named="Compresor") Compresor compresor
    ) {
        return repositoryService.persist(new MantenimientoCompresor(tipoMantenimientoCompresor, compresor));
    }

    // ESTO ES PARA EL DROPDOWNLIST DEL CREAR EQUIPO
    public List<Compresor> choices1Create() {return compresorRepositorio.listAll();}


    @javax.inject.Inject
    RepositoryService repositoryService;

    @javax.inject.Inject
    CompresorRepositorio compresorRepositorio;
}
