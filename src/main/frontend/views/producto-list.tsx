import { ViewConfig } from '@vaadin/hilla-file-router/types.js';
import { Button, DatePicker, Dialog, Grid, GridColumn, GridItemModel, TextArea, TextField, VerticalLayout, ComboBox } from '@vaadin/react-components';
import { Notification } from '@vaadin/react-components/Notification';
import { useSignal } from '@vaadin/hilla-react-signals';
import handleError from 'Frontend/views/_ErrorHandler';
import { Group, ViewToolbar } from 'Frontend/components/ViewToolbar';
import { useDataProvider } from '@vaadin/hilla-react-crud';
import { ProductoService } from 'Frontend/generated/endpoints';
import Producto from 'Frontend/generated/com/unl/music/base/models/Producto';
import { MarcaService } from 'Frontend/generated/endpoints';
import Marca from 'Frontend/generated/com/unl/music/base/models/Marca';
import { useEffect, useState } from 'react';


export const config: ViewConfig = {
  title: 'Producto',
  menu: {
    icon: 'vaadin:clipboard-check',
    order: 2,
    title: 'Productos',
  },
};

type ProductoEntryFormProps = {
  onProductoCreated?: () => void;
};

type ProductoEntryFormPropsUpdate = {
  onProductoUpdated?: () => void;
};

function ProductoEntryForm(props: ProductoEntryFormProps) {
  const dialogOpened = useSignal(false);
  const [Marcas, setMarcas] = useState<Marca[]>([]);

  const open = () => {
    dialogOpened.value = true;

  };

  const close = () => {
    dialogOpened.value = false;
  };

  const nombre = useSignal('');
  const descripcion = useSignal('');
  const id_marca = useSignal(0);

  useEffect(() => {
    const fetchMarcas = async () => {
      const result = await MarcaService.listAllMarca();
      setMarcas(result || []);
    };
    fetchMarcas();
  }, []);

  const createProducto = async () => {
    try {
      if (nombre.value.trim().length > 0 && descripcion.value.trim().length > 0 && id_marca.value > 0) {
        await ProductoService.createProducto(nombre.value, descripcion.value, id_marca.value);
        if (props.onProductoCreated) {
          props.onProductoCreated();
        }
        nombre.value = '';
        descripcion.value = '';
        id_marca.value = 0;
        dialogOpened.value = false;
        Notification.show('Producto creada exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
        aria-label="Registrar Producto"
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
            Registrar Producto
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={createProducto}>
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
              placeholder='Ingrese el nombre de la Marca'
              aria-label='Ingrese el nombre de la Marca'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <TextField label="Descripcion"
              placeholder='Ingrese la descripcion de la Marca'
              aria-label='Ingrese la descripcion de la Marca'
              value={descripcion.value}
              onValueChanged={(evt) => (descripcion.value = evt.detail.value)}
            />
            <ComboBox
              label="Marca"
              placeholder="Seleccione una Marca"
              items={Marcas}
              itemLabelPath="nombre"
              itemValuePath="id"
              value={id_marca.value}
              onValueChanged={(evt) => (id_marca.value = Number(evt.detail.value))}
            />
          </VerticalLayout>
        </VerticalLayout>
      </Dialog>
      <Button theme='registrar' onClick={open}>Registrar</Button>
    </>
  );
}


//UPDATE Marca

function ProductoEntryFormUpdate(props: ProductoEntryFormPropsUpdate) {
  const Producto = props.arguments;
  const dialogOpened = useSignal(false);
  const [Marcas, setMarcas] = useState<Marca[]>([]);

  const open = () => {
    dialogOpened.value = true;
  };

  const close = () => {
    dialogOpened.value = false;
  };
  const ident = props.arguments.id;
  const nombre = useSignal(props.arguments.nombre);
  const descripcion = useSignal(props.arguments.descripcion);
  const id_marca = useSignal(props.arguments.id_Marca);

  useEffect(() => {
    const fetchMarcas = async () => {
      const result = await MarcaService.listAllMarca();
      setMarcas(result || []);
    };
    fetchMarcas();
  }, []);

  const updateProducto = async () => {
    try {
      if (!ident) {
        Notification.show('ID del álbum no válido', { duration: 5000, position: 'top-center', theme: 'error' });
        return;
      }
      if (nombre.value.trim().length > 0 && descripcion.value.trim().length > 0 && id_marca.value > 0) {
        await ProductoService.updateProducto(parseInt(ident), nombre.value, descripcion.value, id_marca.value);
        if (props.onProductoUpdated) {
          props.onProductoUpdated();
        }
        nombre.value = '';
        descripcion.value = '';
        id_marca.value = 0;
        dialogOpened.value = false;
        Notification.show('Producto modificado exitosamente', { duration: 5000, position: 'bottom-end', theme: 'success' });
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
        aria-label="Editar Producto"
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
            Editar Producto
          </h2>
        }
        footerRenderer={() => (
          <>
            <Button onClick={close}>Cancelar</Button>
            <Button theme="primary" onClick={updateProducto}>
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
              placeholder='Ingrese el nombre de la Marca'
              aria-label='Ingrese el nombre de la Marca'
              value={nombre.value}
              onValueChanged={(evt) => (nombre.value = evt.detail.value)}
            />
            <TextField label="Descripcion"
              placeholder='Ingrese la descripcion de la Marca'
              aria-label='Ingrese la descripcion de la Marca'
              value={descripcion.value}
              onValueChanged={(evt) => (descripcion.value = evt.detail.value)}
            />
            <ComboBox
              label="Marca"
              placeholder="Seleccione una Marca"
              items={Marcas}
              itemLabelPath="nombre"
              itemValuePath="id"
              value={id_marca.value}
              onValueChanged={(evt) => (id_marca.value = evt.detail.value)}
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


function index({ model }: { model: GridItemModel<Producto> }) {
  return (
    <span>
      {model.index + 1}
    </span>
  );
}



export default function ProductoListView() {
  const dataProvider = useDataProvider({
    list: () => ProductoService.listAll(),
  });

  function link({ item }: { item: Producto }) {
    return (
      <span>
        <ProductoEntryFormUpdate arguments={item} onProductoUpdated={dataProvider.refresh} />
      </span>
    );
  }

  return (
    <main className="w-full h-full flex flex-col box-border gap-s p-m">
      <ViewToolbar title="Productos">
        <Group>
          <ProductoEntryForm onProductoCreated={dataProvider.refresh} />
        </Group>
      </ViewToolbar>
      <Grid dataProvider={dataProvider.dataProvider}>
        <GridColumn header="Nro" renderer={index} />
        <GridColumn path={"nombre"} header="Nombre" />
        <GridColumn path={"descripcion"} header="Descripcion" />
        <GridColumn path={"id_marca"} header="Marca" />
        <GridColumn header="Acciones" renderer={link} />
      </Grid>
    </main>
  );
}