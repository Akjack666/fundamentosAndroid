package io.keepcoding.filmica.view.listeners

import io.keepcoding.filmica.data.Film

interface OnFilmClickLister {
    fun onClick(film: Film)
}