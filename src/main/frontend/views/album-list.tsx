import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, DatePicker, Dialog, Grid, GridColumn, GridItemModel, TextArea, TextField, VerticalLayout, ComboBox } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { AlbumService } from 'Frontend/generated/endpoints';
import Album from 'Frontend/generated/com/unl/music/base/models/Album';
import { BandaService } from 'Frontend/generated/endpoints';
import Banda from 'Frontend/generated/com/unl/music/base/models/Banda';
import { useEffect, useState } from 'react';


export const config: ViewConfig = {
  title: 'Album',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Albums',
  },
};

type AlbumEntryFormProps = {
  onAlbumCreated?: () => void;
};

type AlbumEntryFormPropsUpdate = {
  onAlbumUpdated?: () => void;
};

function AlbumEntryForm(props: AlbumEntryFormProps) {
  const dialogOpened = useSignal(false);
  const [bandas, setBandas] = useState<Banda[]>([]);

  const open = () => {
    dialogOpened.value = true;

  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal('');
  const fecha = useSignal('');
  const id_banda = useSignal(0);

  useEffect(() => {
    const fetchBandas = async () => {
      const result = await BandaService.listAllBanda();
      setBandas(result || []);
    };
    fetchBandas();
  }, []);

  const createAlbum = async () => {
    try {
      if (nombre.value.trim().length > 0 && fecha.value.trim().length > 0 && id_banda.value > 0) {
        await AlbumService.createAlbum(nombre.value, fecha.value, id_banda.value);
        if (props.onAlbumCreated) {
          props.onAlbumCreated();
        }
        nombre.value = '';
        fecha.value = '';
        id_banda.value = 0;
        dialogOpened.value = false;
        Notification.show('Album creada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo crear, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }

    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  return (
    <>
      <Dialog
        aria-label="Registrar Album"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Registrar Album
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createAlbum}>
              Registrar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Nombre"
              placeholder='Ingrese el nombre de la banda'
              aria-label='Ingrese el nombre de la banda'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <DatePicker
              label="Fecha de creacion"
              placeholder="Seleccione una fecha"
              aria-label="Seleccione una fecha"
              value={fecha.value}
              onValueChanged={(evt) => (fecha.value = evt.detail.value)}
            />
            <ComboBox
              label="Banda"
              placeholder="Seleccione una banda"
              items={bandas}
              itemLabelPath="nombre"
              itemValuePath="id"
              value={id_banda.value}
              onValueChanged={(evt) => (id_banda.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button theme='registrar' onClick={open}>Registrar</Button>
    </>
  );
}


//UPDATE BANDA

function AlbumEntryFormUpdate(props: AlbumEntryFormPropsUpdate) {
  const album = props.arguments;
  const dialogOpened = useSignal(false);
  const [bandas, setBandas] = useState<Banda[]>([]);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal(props.arguments.nombre);
  const fecha = useSignal(props.arguments.fecha);
  const ident = props.arguments.id;
  const id_banda = useSignal(props.arguments.id_banda);

  useEffect(() => {
    const fetchBandas = async () => {
      const result = await BandaService.listAllBanda();
      setBandas(result || []);
    };
    fetchBandas();
  }, []);

  const updateAlbum = async () => {
    try {
      if (!ident) {
        Notification.show('ID del álbum no válido', { duration: 5000, position: 'top-center', theme: 'error' });
        return;
      }

      console.log(`Actualizando album con ID: ${ident}, Nombre: ${nombre.value}, Fecha: ${fecha.value}, Banda ID: ${id_banda.value}`);
      if (nombre.value.trim().length > 0 && fecha.value.trim().length > 0 && id_banda.value > 0) {
        await AlbumService.updateAlbum(parseInt(ident), nombre.value, fecha.value, id_banda.value);
        if (props.onAlbumUpdated) {
          props.onAlbumUpdated();
        }
        nombre.value = '';
        fecha.value = '';
        id_banda.value = -1;
        dialogOpened.value = false;
        Notification.show('Album modificado exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
      } else {
        Notification.show('No se pudo modificar, faltan datos', { duration: 5000, position: 'top-center', theme: 'error' });
      }
    } catch (error) {
      console.log(error);
      handleError(error);
    }
  };

  return (
    <>
      <Dialog
        aria-label="Editar Album"
        draggable
        modeless
        opened={dialogOpened.value}
        onOpenedChanged={(event) => {
          dialogOpened.value = event.detail.value;
        }}
        header={
          <h2
            className="draggable"
            style={{
              flex: 1,
              cursor: 'move',
              margin: 0,
              fontSize: '1.5em',
              fontWeight: 'bold',
              padding: 'var(--lumo-space-m) 0',
            }}
          >
            Editar Album
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateAlbum}>
              Actualizar
            </Button>
          </>
        )}
      >
        <VerticalLayout
          theme="spacing"
          style={{ width: '300px', maxWidth: '100%', alignItems: 'stretch' }}
        >
          <VerticalLayout style={{ alignItems: 'stretch' }}>
            <TextField label="Nombre"
              placeholder='Ingrese el nombre de la banda'
              aria-label='Ingrese el nombre de la banda'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <DatePicker
              label="Fecha de creacion"
              placeholder="Seleccione una fecha"
              aria-label="Seleccione una fecha"
              value={fecha.value}
              onValueChanged={(evt) => (fecha.value = evt.detail.value)}
            />
            <ComboBox
              label="Banda"
              placeholder="Seleccione una banda"
              items={bandas}
              itemLabelPath="nombre"
              itemValuePath="id"
              value={id_banda.value}
              onValueChanged={(evt) => (id_banda.value = evt.detail.value)}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button theme='editar' onClick={open}>Editar</Button>
    </>
  );
}



const dateFormatter = new Intl.DateTimeFormat(undefined, {
  dateStyle: 'medium',
});


function index({ model }: { model: GridItemModel<Album> }) {
  return (
    <span>
      {model.index + 1}
    </span>
  );
}



export default function AlbumListView() {
  const dataProvider = useDataProvider({
    list: () => AlbumService.listAll(),
  });

  function link({ item }: { item: Album }) {
    return (
      <span>
        <AlbumEntryFormUpdate arguments={item} onAlbumUpdated={dataProvider.refresh} />
      </span>
    );
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Albums">
        <Group>
          <AlbumEntryForm onAlbumCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn header="Nro" renderer={index} />
        <GridColumn path="nombre" header="Album" />
        <GridColumn path="fecha" header="Fecha">
          {({ item }) => (item.fecha ? dateFormatter.format(new Date(item.fecha)) : 'Never')}
        </GridColumn>
        <GridColumn path="id_banda" header="Banda" />
        <GridColumn header="Acciones" renderer={link} />
      </Grid>
    </main>
  );
}