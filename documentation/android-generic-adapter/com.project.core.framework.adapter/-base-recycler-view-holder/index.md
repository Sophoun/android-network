[android-generic-adapter](../../index.md) / [com.project.core.framework.adapter](../index.md) / [BaseRecyclerViewHolder](./index.md)

# BaseRecyclerViewHolder

`abstract class BaseRecyclerViewHolder<T>`

### Constructors

| Name | Summary |
|---|---|
| [&lt;init&gt;](-init-.md) | `BaseRecyclerViewHolder(parent: <ERROR CLASS>, layoutRes: `[`Int`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-int/index.html)`)` |

### Functions

| Name | Summary |
|---|---|
| [bind](bind.md) | `open fun bind(data: `[`T`](index.md#T)`): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html) |
| [getLifecycle](get-lifecycle.md) | `open fun getLifecycle(): <ERROR CLASS>` |
| [onStateChanged](on-state-changed.md) | `open fun onStateChanged(state: <ERROR CLASS>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Client need to override this method to update their ui state |
| [setupWithViewModel](setup-with-view-model.md) | `fun setupWithViewModel(vararg viewModel: <ERROR CLASS>): `[`Unit`](https://kotlinlang.org/api/latest/jvm/stdlib/kotlin/-unit/index.html)<br>Attach ViewModel with UiState changed |
